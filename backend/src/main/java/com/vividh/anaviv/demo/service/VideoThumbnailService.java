package com.vividh.anaviv.demo.service;

import com.github.kokorin.jaffree.ffmpeg.FFmpeg;
import com.github.kokorin.jaffree.ffmpeg.UrlInput;
import com.github.kokorin.jaffree.ffmpeg.UrlOutput;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class VideoThumbnailService {

    public void generateVideoThumbnail(String videoId, String sourceFilePath) {
        try {
            String thumbnailPath = "/Users/vividhbhardwaj/Desktop/output/%s/thumbnail.jpeg".formatted(videoId);
            Path outputPath = Path.of(thumbnailPath);

            Path parentDir = outputPath.getParent();
            Files.createDirectories(parentDir);

            FFmpeg.atPath()
                    .addInput(UrlInput.fromPath(Path.of(sourceFilePath)))
                    .addOutput(UrlOutput.toPath(outputPath)
                            .addArguments("-ss", "00:00:01")
                            .addArguments("-vframes", "1"))
                    .execute();
        } catch(IOException e) {
            throw new RuntimeException(e);
        }

    }
}
