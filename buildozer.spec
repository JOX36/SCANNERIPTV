[app]

# Nombre de la aplicación
title = IPTV Super Scanner

# Nombre del paquete (minúsculas, sin espacios)
package.name = iptvsuperscanner

# Dominio del paquete
package.domain = org.jox3

# Carpeta donde está el main.py
source.dir = .

# Archivos a incluir
source.include_exts = py,png,jpg,kv

# Versión de la app
version = 4.5

# Requerimientos de Python
requirements = python3,kivy,aiohttp,python-dotenv,telethon

# Orientación de la pantalla
orientation = portrait

# Pantalla completa (0 = no)
fullscreen = 0

# =============================================
# Configuración importante para Buildozer
# =============================================

# Bootstrap (obligatorio en versiones nuevas)
p4a.bootstrap = sdl2

# API de Android (31 es más estable que 33)
android.api = 31

# API mínima soportada
android.minapi = 24

# Permisos necesarios
android.permissions = INTERNET,ACCESS_NETWORK_STATE

# Color de la pantalla de carga (coincide con el diseño oscuro)
android.presplash_color = #0a001f

[buildozer]

# Nivel de logs (2 = más información)
log_level = 2

# Mostrar advertencia si se ejecuta como root
warn_on_root =  worksp1
