package com.vividh.anaviv.demo.controller;

import com.vividh.anaviv.demo.temporal.WorkerProcess;
import com.vividh.anaviv.demo.temporal.Workflow;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.client.WorkflowStub;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.workflow.WorkflowInterface;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/videos")
public class VideoController {

    private final WorkflowClient client;

    @SneakyThrows
    @PostMapping("/upload")
    public void uploadVideo() {
        String id = "1";
        String videoFilePath = "/Users/vividhbhardwaj/Desktop/testVid.mp4";
        String videoId = "1";
        Workflow workflow = client.newWorkflowStub(Workflow.class,
                WorkflowOptions.newBuilder()
                        .setTaskQueue("video-processing-queue")
                        .setWorkflowId(id)
                        .build());

        WorkflowClient.start(workflow::workflowMethod, videoId, videoFilePath);
    }
}
