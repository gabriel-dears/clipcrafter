package com.gabriel.clipcrafter.presentation.controller;

import com.gabriel.clipcrafter.application.service.YouTubeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/youtube")
public class YouTubeController {

    @Autowired
    private YouTubeService youTubeService;

    @GetMapping("/channel/{channelId}/videos")
    public List<String> getVideosByChannel(@PathVariable String channelId) {
        try {
            return youTubeService.getChannelVideos(channelId);
        } catch (IOException e) {
            e.printStackTrace();
            return List.of("Error fetching videos: " + e.getMessage());
        }
    }
}
