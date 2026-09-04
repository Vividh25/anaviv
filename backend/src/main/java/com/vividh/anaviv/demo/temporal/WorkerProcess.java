package com.vividh.anaviv.demo.temporal;

import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WorkerProcess {

    @Bean
    public WorkflowServiceStubs createWorkflowServiceStubs() {
        return WorkflowServiceStubs.newServiceStubs(
                WorkflowServiceStubsOptions.newBuilder()
                        .setTarget("localhost:7233")
                        .build()
        );
    }

    @Bean
    public WorkflowClient createWorkflowClient(WorkflowServiceStubs service) { return WorkflowClient.newInstance(service); }

    @Bean
    public WorkerFactory createWorkerFactory(WorkflowClient client) { return WorkerFactory.newInstance(client); }

    @Bean
    public Worker createWorker(WorkerFactory factory, ActivitiesImpl activities) {

        String taskQueue = "video-processing-queue";
        Worker worker = factory.newWorker(taskQueue);

        worker.registerWorkflowImplementationTypes(WorkflowImpl.class);
        worker.registerActivitiesImplementations(activities);

        return worker;
    }

    @Bean
    public ApplicationRunner startWorkerFactory(WorkerFactory factory) {
        return args -> factory.start();
    }

}
