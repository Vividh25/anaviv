package com.vividh.anaviv.demo.temporal;

import com.vividh.anaviv.demo.enums.Resolution;
import com.vividh.anaviv.demo.record.TranscodeResult;
import com.vividh.anaviv.demo.record.VideoMetadata;
import com.vividh.anaviv.demo.service.*;
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
    private final VideoManifestService videoManifestService;
    private final VideoThumbnailService videoThumbnailService;

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

    @Override
    public void getMasterManifest(String videoId) {
        videoManifestService.generateMasterManifest(videoId);
    }

    @Override
    public void generateThumbnail(String videoId, String videoFilePath) {
        videoThumbnailService.generateVideoThumbnail(videoId, videoFilePath);
    }

    @Override
    public void markVideoReady(String videoId) {
        log.info("Video ready: Video Id={}", videoId);
    }
}