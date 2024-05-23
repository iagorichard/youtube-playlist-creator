package com.irrs.youtube.playlist.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.irrs.youtube.playlist.demo.service.YTService;

@RestController
public class YTController{

    @Autowired
    private YTService ytService;

    @PostMapping(value="upload/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void createPlaylistHandler(@RequestParam("YTAPIKey") String YTAPIKey, 
                                      @RequestParam("file") MultipartFile file){
        List<String> musicList = ytService.getMusicListFromFile(file);
        List<String> musicsID = ytService.getAllMusicsList(musicList, YTAPIKey);
        //ytService.createPlaylist(musicsID, YTAPIKey);
    }

}