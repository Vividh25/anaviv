package com.vividh.anaviv.demo.temporal;

import com.vividh.anaviv.demo.enums.Resolution;
import com.vividh.anaviv.demo.record.TranscodeResult;
import io.temporal.activity.ActivityOptions;
import io.temporal.workflow.Async;
import io.temporal.workflow.Promise;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class WorkflowImpl implements Workflow {

    private final Activities activities = io.temporal.workflow.Workflow.newActivityStub(
            Activities.class,
            ActivityOptions.newBuilder()
                    .setStartToCloseTimeout(Duration.ofSeconds(100))
                    .build()
    );

    @SneakyThrows
    @Override
    public void workflowMethod(String videoId, String videoFilePath) {
        activities.activitiesMethod1(videoId, videoFilePath);

        List<Promise<TranscodeResult>> transcodePromises = new ArrayList<>();

        for (Resolution resolution : Resolution.values()) {
            Promise<TranscodeResult> promise = Async.function(activities::transcode, videoId, videoFilePath, resolution);
            transcodePromises.add(promise);
        }

        List<TranscodeResult> results = transcodePromises.stream()
                .map(Promise::get)
                .toList();

        List<Promise<Void>> segmentPromises = results.stream()
                        .map(result -> Async.procedure(activities::segment, result))
                        .toList();

        segmentPromises.forEach(Promise::get);
    }
}
