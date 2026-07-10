// Para llamadas HTTP (validación de listas + envío a Telegram Bot)
implementation("io.ktor:ktor-client-core:2.3.12")
implementation("io.ktor:ktor-client-cio:2.3.12")
implementation("io.ktor:ktor-client-content-negotiation:2.3.12")
implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.12")

// DataStore ya está agregado

// === TDLib (Opción recomendada para escanear chats) ===
// NOTA: TDLib requiere configuración nativa más avanzada.
// Por ahora lo dejamos comentado. Te lo activo cuando estemos listos.
