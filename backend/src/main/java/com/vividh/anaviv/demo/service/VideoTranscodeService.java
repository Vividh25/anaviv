package com.vividh.anaviv.demo.service;

import com.github.kokorin.jaffree.ffmpeg.FFmpeg;
import com.github.kokorin.jaffree.ffmpeg.UrlInput;
import com.github.kokorin.jaffree.ffmpeg.UrlOutput;
import com.vividh.anaviv.demo.enums.Resolution;
import com.vividh.anaviv.demo.record.TranscodeResult;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class VideoTranscodeService {

    public TranscodeResult transcode(String videoId, String inputPath, Resolution resolution) {

        try {

            String outputPath = "/Users/vividhbhardwaj/Desktop/output/%s/%s/video.mp4".formatted(videoId, resolution);
            Path outputFilePath = Path.of(outputPath);

            Path parentDir = outputFilePath.getParent();

            Files.createDirectories(parentDir);

            FFmpeg.atPath()
                    .addInput(UrlInput.fromPath(Paths.get(inputPath)))
                    .addOutput(UrlOutput.toPath(outputFilePath)
                            .addArguments("-vf", "scale=%d:%d".formatted(resolution.width, resolution.height))
                            .addArguments("-g", "30")
                            .addArguments("-keyint_min", "30"))
                    .execute();

            return new TranscodeResult(videoId, resolution, outputPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
