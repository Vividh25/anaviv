import { registerServiceWorker } from "./pwa/registerServiceWorker.js";

// TODO: replace with real routing (login -> timeline -> player) once the
// auth and timeline views exist. This just proves the scaffold boots.
document.querySelector("#app").innerHTML = `
  <main>
    <h1>Anaviv</h1>
    <p>Scaffold running. Login, timeline, and player views land here.</p>
  </main>
`;

registerServiceWorker();
