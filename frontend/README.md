# Anaviv — frontend

Mobile-first (Android-focused) PWA client. Vanilla JS + [Vite](https://vitejs.dev)
scaffold for now — swap in a framework later if the app outgrows this.

## Structure

```
frontend/
├── index.html              # app shell
├── public/
│   ├── manifest.json        # PWA manifest ("Add to Home Screen")
│   ├── sw.js                 # service worker (app-shell cache; never caches HLS/API)
│   └── icons/                 # 192/512 PWA icons
├── src/
│   ├── main.js                 # entry point
│   ├── api/client.js            # fetch wrapper for the Spring Boot backend
│   ├── player/VideoPlayer.js     # hls.js adaptive-bitrate playback
│   ├── pwa/registerServiceWorker.js
│   └── styles/main.css
└── vite.config.js            # dev proxy -> backend on :8080
```

## Getting started

```bash
npm install
npm run dev
```

Requires the backend running on `:8080` (see `../backend/README.md` /
root `README.md`) — `/api` and `/hls` requests are proxied there in dev.
