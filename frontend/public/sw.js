// Anaviv service worker
//
// Scope: enables "Add to Home Screen" / installable PWA behavior and a
// minimal app-shell cache. Video/HLS traffic is intentionally NOT cached
// here — segments are large, per-session, and should always hit the
// network (or a CDN in front of S3-compatible storage).

const APP_SHELL_CACHE = "anaviv-app-shell-v1";
const APP_SHELL_ASSETS = ["/", "/index.html", "/manifest.json"];

self.addEventListener("install", (event) => {
  event.waitUntil(
    caches
      .open(APP_SHELL_CACHE)
      .then((cache) => cache.addAll(APP_SHELL_ASSETS))
      .then(() => self.skipWaiting())
  );
});

self.addEventListener("activate", (event) => {
  event.waitUntil(
    caches
      .keys()
      .then((keys) =>
        Promise.all(
          keys
            .filter((key) => key !== APP_SHELL_CACHE)
            .map((key) => caches.delete(key))
        )
      )
      .then(() => self.clients.claim())
  );
});

self.addEventListener("fetch", (event) => {
  const { request } = event;

  // Never intercept HLS manifests/segments or API calls — always network.
  if (
    request.url.includes("/hls/") ||
    request.url.endsWith(".m3u8") ||
    request.url.endsWith(".ts") ||
    request.url.includes("/api/")
  ) {
    return;
  }

  event.respondWith(
    caches.match(request).then((cached) => cached || fetch(request))
  );
});
