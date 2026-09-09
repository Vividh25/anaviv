package com.vividh.anaviv.demo.service;

import com.vividh.anaviv.demo.enums.Resolution;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

@Service
public class VideoManifestService {

    public void generateMasterManifest(String videoId) {
        try {
            String masterManifestPath = "/Users/vividhbhardwaj/Desktop/output/%s/master.m3u8".formatted(videoId);

            Path outputPath = Path.of(masterManifestPath);
            Files.createDirectories(outputPath.getParent());

            StringBuilder sb = new StringBuilder();
            sb.append("#EXTM3U\n");
            sb.append("#EXT-X-VERSION:3\n");

            for (Resolution resolution : Resolution.values()) {
                sb.append("#EXT-X-STREAM-INF:BANDWIDTH=%d,RESOLUTION=%dx%d\\n".formatted(resolution.bandwidth, resolution.width, resolution.height));
                sb.append("%s/manifest.m3u8\n".formatted(resolution));
            }

            Files.writeString(outputPath, sb.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
