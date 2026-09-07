package com.vividh.anaviv.demo.temporal;

import com.vividh.anaviv.demo.enums.Resolution;
import com.vividh.anaviv.demo.record.TranscodeResult;
import com.vividh.anaviv.demo.record.VideoMetadata;
import com.vividh.anaviv.demo.service.VideoMetadataService;
import com.vividh.anaviv.demo.service.VideoSegmentService;
import com.vividh.anaviv.demo.service.VideoTranscodeService;
import io.temporal.workflow.Async;
import io.temporal.workflow.Promise;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class ActivitiesImpl implements Activities {

    private final VideoMetadataService videoMetadataService;
    private final VideoTranscodeService videoTranscodeService;
    private final VideoSegmentService videoSegmentService;

    @Override
    public void activitiesMethod1(String videoId, String videoFilePath) {
        VideoMetadata videoMetadata = videoMetadataService.getVideoMetadata(videoId, videoFilePath);
        log.info("VIDEO METADATA: {}", String.valueOf(videoMetadata));
    }

    @Override
    public TranscodeResult transcode(String videoId, String inputPath, Resolution resolution) {
        return videoTranscodeService.transcode(videoId, inputPath, resolution);
    }

    @Override
    public void segment(TranscodeResult transcodeResult) {
        videoSegmentService.createVideoSegment(transcodeResult);
    }
}