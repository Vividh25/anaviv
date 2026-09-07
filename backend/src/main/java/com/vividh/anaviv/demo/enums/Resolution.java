package com.vividh.anaviv.demo.enums;

public enum Resolution {
    R_1080P(1920, 1080),
    R_720P(1280, 720),
    R_480P(854, 360),
    R_360P(640, 360);


    public final int width;
    public final int height;

    Resolution(int width, int height) {
        this.width = width;
        this.height = height;
    }
}
