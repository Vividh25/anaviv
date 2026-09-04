package com.vividh.anaviv.demo.temporal;

import com.vividh.anaviv.demo.record.VideoMetadata;
import com.vividh.anaviv.demo.service.VideoMetadataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class ActivitiesImpl implements Activities {

    private final VideoMetadataService videoMetadataService;

    @Override
    public void activitiesMethod1(String videoId, String videoFilePath) {
        VideoMetadata videoMetadata = videoMetadataService.getVideoMetadata(videoId, videoFilePath);
        log.info(String.valueOf(videoMetadata));
    }

    @Override
    public void activitiesMethod2() {
        log.info("Activity2 executing...");
        sleep(90000);
        log.info("Activity2 Completed");
    }

    @Override
    public void activitiesMethod3() {
        log.info("Activity3 executing...");
        sleep(30000);
        log.info("Activity3 Completed");
    }

    @Override
    public void activitiesMethod4() {
        log.info("Activity4 executing...");
        sleep(80000);
        log.info("Activity4 Completed");
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }
}