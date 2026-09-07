package com.vividh.anaviv.demo.record;

import com.vividh.anaviv.demo.enums.Resolution;

public record TranscodeResult(String videoId, Resolution resolution, String outputPath) {
}
