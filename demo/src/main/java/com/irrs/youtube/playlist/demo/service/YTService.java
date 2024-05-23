package com.irrs.youtube.playlist.demo.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class YTService{

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public List<String> getMusicListFromFile(MultipartFile file) throws RuntimeException{

        List<String> musicList = new ArrayList<String>();

        try (
            BufferedReader reader = new BufferedReader(
                new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8)
                )
            ) {
                String line;
                while ((line = reader.readLine()) != null) {
                    musicList.add(line);
                }
        } catch (IOException e) {
            throw new RuntimeException("Could not open the file!");
        }

        return musicList;
    }

    public String getJSONIDMusicInfo(String response) throws RuntimeException{
        // Parse the JSON response to a JsonNode
        try {
            JsonNode root = objectMapper.readTree(response);
            // Get the videoId from the response
            JsonNode items = root.path("items");
            if (items.isArray() && items.size() > 0) {
                JsonNode firstItem = items.get(0);
                JsonNode videoIdNode = firstItem.path("id").path("videoId");
                if (!videoIdNode.isMissingNode()) {
                    return videoIdNode.asText();
                } else {
                    throw new RuntimeException("Could not find the videoId in the JSON response!");
                }
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Could not parse the JSON response!");
        }

        throw new RuntimeException("Could not parse the JSON response!");
        
    }

    public String getMusicID(String musicString, String YTAPIKey){

        final String BASE_URL = "https://www.googleapis.com/youtube/v3/search";
        final String URL = BASE_URL + "?maxResults=1&q="+
                           musicString+"&key="+YTAPIKey;

        final String response = restTemplate.getForObject(URL, String.class);

        return getJSONIDMusicInfo(response);
    }

    public List<String> getAllMusicsList(List<String> musicList, String YTAPIKey){
        
        List<String> musicsID = new ArrayList<>();
        
        for (String musicString : musicList) {
            musicsID.add(getMusicID(musicString, YTAPIKey));
        }

        for (String musicID : musicsID){
            System.out.println(musicID);
        }

        return musicsID;
    }

    public void createPlaylist(List<String> musicsID, String YTAPIKey){

        final String URL = "https://www.googleapis.com/youtube/v3/playlists";
        final Hashtable<String, Object> params = new Hashtable<>();

        params.put("key", YTAPIKey);
        params.put("part", musicsID);

        String response = restTemplate.postForObject(URL, params, String.class);

        System.out.println(response);
    }

}