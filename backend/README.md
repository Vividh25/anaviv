# Anaviv — backend

Spring Boot service exposing the upload/playback API and hosting the
Temporal workflow worker for the video processing pipeline.

## Stack

- Spring Boot (Web MVC)
- [Temporal Java SDK](https://docs.temporal.io/dev-guide/java) — workflow orchestration
- [Jaffree](https://github.com/kokorin/Jaffree) — FFmpeg/ffprobe wrapper for transcoding, HLS segmenting, and metadata extraction
- Lombok

## Structure

```
src/main/java/com/vividh/anaviv/demo/
├── controller/     # REST endpoints (upload, playback, etc.)
├── service/        # application services (metadata, storage)
├── record/         # data records (e.g. VideoMetadata)
└── temporal/        # Workflow/Activity interfaces + impls, worker setup
```

## Running locally

Requires a Temporal server reachable at `localhost:7233`:

```bash
temporal server start-dev
```

Then:

```bash
./mvnw spring-boot:run
```

## Pipeline

An upload kicks off a Temporal workflow that:

1. Extracts metadata (`ffprobe`)
2. Fans out one activity per target resolution/bitrate (`ffmpeg` transcode, via Jaffree)
3. Fans in once all renditions complete
4. Segments each rendition into HLS (`.m3u8` + `.ts` chunks)
5. Generates the HLS master manifest and publishes everything to blob storage

TODO: S3-compatible storage client, Cassandra metadata store, and the
two-user auth layer are not wired up yet.
