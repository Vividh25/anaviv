# Anaviv

A private, invite-only video streaming app for two people. Upload a video
from your phone, and it shows up — adaptively streamed, organized on a
shared timeline — for the both of you. No public access, no discovery feed,
no algorithm. Just yours.

## How it works

```
Upload (phone/device)
   │
   ▼
Spring Boot API  ──►  Temporal workflow: Upload → Transcode → Segment → Manifest → Publish
                            │
                            ├── fan-out: transcode per resolution/bitrate (parallel, FFmpeg via Jaffree)
                            ├── fan-in: wait for all renditions
                            ├── segment into HLS (.m3u8 + .ts chunks)
                            └── publish: write manifest + segments to blob storage
   │
   ▼
S3-compatible storage (video/segments)      Cassandra (video metadata)
   │
   ▼
hls.js (client) ──► adaptive bitrate playback in the browser/PWA
```

Each upload becomes a Temporal workflow execution. Resolutions/bitrates are
transcoded in parallel activities (fan-out), the workflow waits on all of
them (fan-in), then generates the HLS master + variant manifests and
publishes everything before the video is marked playable.

## Architecture

| Layer | Choice | Why |
|---|---|---|
| Backend | Java, Spring Boot | REST API, auth, orchestration entrypoint |
| Orchestration | [Temporal](https://temporal.io) | Durable, resumable multi-step pipeline (upload → transcode → segment → manifest → publish) with parallel fan-out/fan-in per resolution and built-in retries |
| Video processing | FFmpeg via [Jaffree](https://github.com/kokorin/Jaffree), ffprobe | Transcoding, HLS segmenting, metadata extraction |
| Object storage | S3-compatible | Video source files + HLS segments |
| Metadata store | Cassandra | High availability, write-heavy access pattern (uploads, comments, reactions) |
| Frontend | Vite + vanilla JS PWA, [hls.js](https://github.com/video-dev/hls.js) | Mobile-first (Android-focused), installable via manifest.json + service worker |
| Auth | Two-user private login | No public access, no discovery/feed |

## Repo layout

```
anaviv/
├── backend/     # Spring Boot service, Temporal workflows/activities
└── frontend/    # PWA client (Vite, hls.js)
```

See [`backend/README.md`](backend/README.md) *(TODO)* and
[`frontend/README.md`](frontend/README.md) for service-specific setup.

## Getting started

**Backend**
```bash
cd backend
./mvnw spring-boot:run
```

**Frontend**
```bash
cd frontend
npm install
npm run dev
```

You'll also need a local Temporal server (`temporal server start-dev`), an
S3-compatible store (e.g. MinIO), and Cassandra running for the full
pipeline to work end to end.

## Planned features

Beyond core upload → transcode → stream:

- **Shared memory timeline** — videos organized chronologically with dates and captions
- **Private comments/reactions** — timestamped, just-the-two-of-you
- **"On this day"** — resurfaces old videos from the same date in past years
- **Mini-games** — a couple-only section (trivia, simple two-player games)

## Status

Early scaffold. Core pipeline (upload → transcode → segment → manifest →
publish) is under active development; the features above are planned, not
yet built.
