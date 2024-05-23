package com.irrs.youtube.playlist.demo.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Service
public class YTService{

    @Autowired
    private RestTemplate restTemplate = new RestTemplate();

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

    public String getMusicID(String musicString, String YTAPIKey){

        final String BASE_URL = "https://www.googleapis.com/youtube/v3/search";
        final String URL = BASE_URL + "?maxResults=1&q="+
                           musicString+"&key="+YTAPIKey;

        final String response = restTemplate.getForObject(URL, String.class);

        return response;
    }

    public List<String> getAllMusicsList(List<String> musicList, String YTAPIKey){
        
        List<String> musicsID = new ArrayList<>();
        
        for (String musicString : musicList) {
            musicsID.add(getMusicID(musicString, YTAPIKey));
        }

        return musicsID;
    }

    public void createPlaylist(List<String> musicsID){

        //requisição post para criar a playlist

    }

}