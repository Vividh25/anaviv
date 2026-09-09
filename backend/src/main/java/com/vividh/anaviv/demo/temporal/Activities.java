package com.vividh.anaviv.demo.temporal;

import com.vividh.anaviv.demo.enums.Resolution;
import com.vividh.anaviv.demo.record.TranscodeResult;
import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

import java.io.IOException;

@ActivityInterface
public interface Activities {

    @ActivityMethod
    void activitiesMethod1(String videoId, String videoFilePath);

    @ActivityMethod
    TranscodeResult transcode(String videoId, String inputPath, Resolution resolution);

    @ActivityMethod
    void segment(TranscodeResult transcodeResult);

    @ActivityMethod
    void getMasterManifest(String videoId);

    @ActivityMethod
    void generateThumbnail(String videoId, String videoFilePath);

    @ActivityMethod
    void markVideoReady(String videoId);
}
