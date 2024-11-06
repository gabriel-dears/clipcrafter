package com.gabriel.clipcrafter.application.service;

import com.gabriel.clipcrafter.application.command_runner.CommandResult;
import com.gabriel.clipcrafter.application.command_runner.ProcessBuilderSyncCommandRunner;
import com.gabriel.clipcrafter.application.command_runner.SyncCommandRunner;
import com.gabriel.clipcrafter.application.dto.ClipRequest;
import com.gabriel.clipcrafter.application.dto.ClipResponse;
import com.gabriel.clipcrafter.infrastructure.util.VideoProcessingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class ClipCreatorService {

    @Autowired
    private YouTubeService youTubeService;

    @Autowired
    private VideoProcessingUtil videoProcessingUtil;

    @Autowired
    private QueryParamExtractor queryParamExtractor;

    public ClipResponse createClips(ClipRequest clipRequest) {
        try {
            List<String> videos = youTubeService.getChannelVideos(clipRequest.getChannelId());
            for (String videoUrl : videos) {

                Map<String, String> queryParams = queryParamExtractor.getQueryParams(videoUrl);

                String videoId = queryParams.get("v");

                // Download the video
                int exitCode = videoProcessingUtil.downloadVideo(videoUrl, videoId);

                // Cut the video (example: cut from 00:00:10 to 5 seconds)
                if (exitCode == 0) {

                    SyncCommandRunner syncCommandRunner = new ProcessBuilderSyncCommandRunner();
                    syncCommandRunner.command("ls");
                    syncCommandRunner.directory(new File("/home/gabriel/Documents/portfolio/clipcrafter/target/clips/"));
                    CommandResult commandResult = syncCommandRunner.execute();

                    String resultFromLsExecution = commandResult.getOutput();

                    String[] splitResult = resultFromLsExecution.split("\\n");
                    Set<String> splitResultAsSet = Set.of(splitResult);

                    String videoIdWithExtension = "";

                    for (String videoIdAndExtension : splitResultAsSet) {
                        if(videoIdAndExtension.contains(videoId)) {
                            videoIdWithExtension = videoIdAndExtension;
                            break;
                        }
                    }

                    String outputPath = "cut_" + videoIdWithExtension;
                    videoProcessingUtil.cutVideo(videoIdWithExtension, outputPath, "00:00:10", "5");
                }
            }

            return new ClipResponse("Clips created successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            return new ClipResponse("Error creating clips: " + e.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
