package com.vividh.anaviv.demo.service;

import com.github.kokorin.jaffree.StreamType;
import com.github.kokorin.jaffree.ffprobe.FFprobe;
import com.github.kokorin.jaffree.ffprobe.FFprobeResult;
import com.github.kokorin.jaffree.ffprobe.Stream;
import com.vividh.anaviv.demo.record.VideoMetadata;
import org.springframework.stereotype.Service;

@Service
public class VideoMetadataService {

    public VideoMetadata getVideoMetadata(String videoId, String videoFilePath) {
        FFprobeResult result = FFprobe.atPath()
                .setInput(videoFilePath)
                .setShowStreams(true)
                .setShowFormat(true)
                .execute();

        Stream videoStream = result.getStreams().stream()
                .filter(s -> s.getCodecType() == StreamType.VIDEO)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No video stream found for " + videoFilePath));

        double duration = result.getFormat().getDuration();
        String codecName = videoStream.getCodecName();
        int width = videoStream.getWidth();
        int height = videoStream.getHeight();

        return new VideoMetadata(videoId, duration, codecName, width, height);

    }
}
