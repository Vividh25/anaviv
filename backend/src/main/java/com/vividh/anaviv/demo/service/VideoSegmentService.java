package com.vividh.anaviv.demo.service;

import com.github.kokorin.jaffree.ffmpeg.FFmpeg;
import com.github.kokorin.jaffree.ffmpeg.UrlInput;
import com.github.kokorin.jaffree.ffmpeg.UrlOutput;
import com.vividh.anaviv.demo.record.TranscodeResult;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class VideoSegmentService {

    public void createVideoSegment(TranscodeResult transcodeResult) {

        try {
            String manifestPath = "/Users/vividhbhardwaj/Desktop/output/%s/%s/manifest.m3u8".formatted(transcodeResult.videoId(), transcodeResult.resolution());
            String segmentPattern = "/Users/vividhbhardwaj/Desktop/output/%s/%s/segment_%%03d.ts".formatted(transcodeResult.videoId(), transcodeResult.resolution());

            Path outputManifestPath = Path.of(manifestPath);

            Path parentDir = outputManifestPath.getParent();

            Files.createDirectories(parentDir);

            FFmpeg.atPath()
                    .addInput(UrlInput.fromPath(Paths.get(transcodeResult.outputPath())))
                    .addOutput(UrlOutput.toPath(outputManifestPath)
                            .addArguments("-c", "copy")
                            .addArguments("-hls_time", "1")
                            .addArguments("-hls_playlist_type", "vod")
                            .addArguments("-hls_segment_filename", segmentPattern))
                    .execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
