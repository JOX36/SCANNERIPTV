[app]

# (str) Title of your application
title = IPTV Super Scanner

# (str) Package name in lowercase
package.name = iptvsuperscanner

# (str) Package domain (needed for android/ios packaging)
package.domain = org.jox3

# (str) Source code where the main.py is
source.dir = .

# (list) Source files to include (leave empty to include all the files)
source.include_exts = py,png,jpg,kv

# (str) Application versioning
version = 4.5

# (list) Application requirements
requirements = python3,kivy,aiohttp,python-dotenv,telethon

# (str) Supported orientation (portrait or landscape)
orientation = portrait

# (list) Permissions
android.permissions = INTERNET,ACCESS_NETWORK_STATE

# (int) Android API to use (31 is more stable than 33 on GitHub Actions)
android.api = 31

# (int) Minimum Android API your app will support
android.minapi = 24

# (bool) Indicate if the application should be fullscreen or not
fullscreen = 0

# (str) Bootstrap to use
android.bootstrap = sdl2

# (bool) Use --private data storage
android.private_storage = True

# (str) Presplash background color
android.presplash_color = #0a001f

[buildozer]

# (int) Log level (2 = debug)
log_level = 2

# (int) Display warning if buildozer is run as root
warn_on_root = 1
