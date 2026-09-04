import Hls from "hls.js";

// Mounts adaptive-bitrate HLS playback into `videoEl` for the given
// manifest URL (a master .m3u8 produced by the backend's publish step).
// Falls back to native HLS for Safari/iOS, which supports it out of the box.
export function mountPlayer(videoEl, manifestUrl) {
  if (Hls.isSupported()) {
    const hls = new Hls();
    hls.loadSource(manifestUrl);
    hls.attachMedia(videoEl);
    return () => hls.destroy();
  }

  if (videoEl.canPlayType("application/vnd.apple.mpegurl")) {
    videoEl.src = manifestUrl;
    return () => {
      videoEl.removeAttribute("src");
      videoEl.load();
    };
  }

  console.error("[player] HLS playback is not supported in this browser");
  return () => {};
}
