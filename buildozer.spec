[app]

title = IPTV Super Scanner
package.name = iptvsuperscanner
package.domain = org.jox3
source.dir = .
source.include_exts = py,png,jpg,kv
version = 4.5

requirements = python3,kivy,aiohttp,python-dotenv,telethon

orientation = portrait
fullscreen = 0

# === Configuración importante para GitHub Actions ===
p4a.bootstrap = sdl2
android.api = 31
android.minapi = 24
android.permissions = INTERNET,ACCESS_NETWORK_STATE

[buildozer]
log_level = 2
warn_on_root = 1
