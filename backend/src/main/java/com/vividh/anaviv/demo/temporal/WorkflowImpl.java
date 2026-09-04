package com.vividh.anaviv.demo.temporal;

import io.temporal.activity.ActivityOptions;
import io.temporal.workflow.Async;
import io.temporal.workflow.Promise;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

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

        Promise<Void> am2Promise = Async.procedure(activities::activitiesMethod2);
        Promise<Void> am3Promise = Async.procedure(activities::activitiesMethod3);
        Promise<Void> am4Promise = Async.procedure(activities::activitiesMethod4);

        am2Promise.get();
        am3Promise.get();
        am4Promise.get();
    }
}
