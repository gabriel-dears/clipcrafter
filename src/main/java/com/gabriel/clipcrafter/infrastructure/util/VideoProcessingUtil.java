package com.gabriel.clipcrafter.infrastructure.util;

import com.gabriel.clipcrafter.application.command_runner.*;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class VideoProcessingUtil {

    public int downloadVideo(String videoUrl, String videoId) throws IOException, InterruptedException {
        SyncCommandRunner syncCommandRunner = new ProcessBuilderSyncCommandRunner();
        syncCommandRunner.command("yt-dlp", "-o",  videoId + ".%(ext)s", videoUrl);
        syncCommandRunner.directory(new File("/home/gabriel/Documents/portfolio/clipcrafter/target/clips/"));
        CommandResult commandResult = syncCommandRunner.execute();
        return commandResult.getExitCode();
    }

    public void cutVideo(String videoId, String outputFilePath, String startTime, String duration) throws IOException {
        AsyncCommandRunner asyncCommandRunner = new ProcessBuilderAsyncCommandRunner();
        asyncCommandRunner.command("ffmpeg", "-i", videoId, "-ss", startTime, "-t", duration, "-c", "copy", outputFilePath);
        asyncCommandRunner.directory(new File("/home/gabriel/Documents/portfolio/clipcrafter/target/clips/"));
        asyncCommandRunner.executeAsync();
    }
}

