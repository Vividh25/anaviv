package com.vividh.anaviv.demo.temporal;

import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface Activities {

    @ActivityMethod
    void activitiesMethod1(String videoId, String videoFilePath);

    @ActivityMethod
    void activitiesMethod2();

    @ActivityMethod
    void activitiesMethod3();

    @ActivityMethod
    void activitiesMethod4();
}
