package com.vividh.anaviv.demo.temporal;

import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface Workflow {

    @WorkflowMethod
    void workflowMethod(String videoId, String videoFilePath);
}
