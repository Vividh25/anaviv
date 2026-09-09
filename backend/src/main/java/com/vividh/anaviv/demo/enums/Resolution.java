package com.vividh.anaviv.demo.enums;

public enum Resolution {
    R_1080P(1920, 1080, 5_000_000),
    R_720P(1280, 720, 2_800_000),
    R_480P(854, 480, 1_400_000),
    R_360P(640, 360, 800_000);


    public final int width;
    public final int height;
    public final int bandwidth ;

    Resolution(int width, int height, int bandwidth) {
        this.width = width;
        this.height = height;
        this.bandwidth = bandwidth;
    }
}
