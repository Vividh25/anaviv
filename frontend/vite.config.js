import { defineConfig } from "vite";

export default defineConfig({
  server: {
    proxy: {
      // Forwards API + HLS playback requests to the Spring Boot backend
      // during local development.
      "/api": "http://localhost:8080",
      "/hls": "http://localhost:8080",
    },
  },
});
