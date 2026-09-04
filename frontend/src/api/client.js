// Thin fetch wrapper for talking to the Spring Boot backend.
// TODO: wire up auth (session/JWT for the two-user login) once the
// backend auth endpoints exist.

const BASE_URL = import.meta.env.VITE_API_BASE_URL ?? "/api";

async function request(path, options = {}) {
  const res = await fetch(`${BASE_URL}${path}`, {
    credentials: "include",
    headers: { "Content-Type": "application/json", ...options.headers },
    ...options,
  });

  if (!res.ok) {
    throw new Error(`API request failed: ${options.method ?? "GET"} ${path} -> ${res.status}`);
  }

  return res.status === 204 ? null : res.json();
}

export const api = {
  get: (path) => request(path),
  post: (path, body) => request(path, { method: "POST", body: JSON.stringify(body) }),

  // Kicks off the upload -> transcode -> segment -> manifest Temporal
  // workflow on the backend once the video bytes are uploaded.
  uploadVideo: (formData) =>
    fetch(`${BASE_URL}/videos/upload`, {
      method: "POST",
      credentials: "include",
      body: formData,
    }),

  // Returns the HLS master manifest URL for a given video once published.
  getPlaybackManifestUrl: (videoId) => `${BASE_URL}/videos/${videoId}/hls/master.m3u8`,
};
