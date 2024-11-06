package com.gabriel.clipcrafter.presentation.controller;

import com.gabriel.clipcrafter.application.dto.ClipRequest;
import com.gabriel.clipcrafter.application.dto.ClipResponse;
import com.gabriel.clipcrafter.application.service.ClipCreatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/youtube")
public class ClipCreatorController {

    @Autowired
    private ClipCreatorService clipCreatorService;

    @PostMapping("/clips")
    public ClipResponse createClips(@RequestBody ClipRequest clipRequest) throws IOException {
        return clipCreatorService.createClips(clipRequest);
    }
}

