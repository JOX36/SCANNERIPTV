#!/usr/bin/env python3
"""
IPTV Super Scanner v4.5 - Android App (VERSIÓN FINAL REAL)
Escaneo real + Validación completa + Envío real a Telegram
"""

from kivy.app import App
from kivy.uix.screenmanager import ScreenManager, Screen
from kivy.uix.boxlayout import BoxLayout
from kivy.uix.button import Button
from kivy.uix.label import Label
from kivy.uix.textinput import TextInput
from kivy.uix.progressbar import ProgressBar
from kivy.uix.scrollview import ScrollView
from kivy.clock import Clock
from kivy.properties import StringProperty, NumericProperty
from kivy.lang import Builder
from kivy.utils import get_color_from_hex
from kivy.storage.jsonstore import JsonStore
import threading
import asyncio
import os
import re
import json
from datetime import datetime
from typing import Dict, Optional, List
from urllib.parse import urlparse

# ==================== COLORES ====================
PRIMARY = get_color_from_hex("#00ffcc")
SECONDARY = get_color_from_hex("#ff00ff")
TEXT_WHITE = get_color_from_hex("#ffffff")
SUCCESS = get_color_from_hex("#00ff88")
INPUT_BG = get_color_from_hex("#16213e")

KV = '''
#:import get_color_from_hex kivy.utils.get_color_from_hex

<MainScreen>:
    name: 'main'
    BoxLayout:
        orientation: 'vertical'
        canvas.before:
            Color:
                rgba: 0.039, 0, 0.122, 1
            Rectangle:
                pos: self.pos
                size: self.size

        BoxLayout:
            size_hint_y: 0.11
            padding: 12
            canvas.before:
                Color:
                    rgba: get_color_from_hex("#00ffcc")
                Rectangle:
                    pos: self.pos
                    size: self.size
            Label:
                text: "🌌 IPTV SUPER SCANNER v4.5 (FINAL REAL)"
                font_size: "18sp"
                bold: True
                color: 0, 0, 0, 1

        BoxLayout:
            orientation: 'vertical'
            padding: 14
            spacing: 6

            Label:
                text: root.status_text
                font_size: "13sp"
                color: PRIMARY

            ProgressBar:
                id: progress
                max: 100
                value: root.progress_value

            Label:
                text: f"Progreso: {root.progress_value:.0f}%"
                font_size: "13sp"
                color: PRIMARY

            ScrollView:
                size_hint_y: 0.6
                canvas.before:
                    Color:
                        rgba: 0.1, 0, 0.2, 1
                    RoundedRectangle:
                        pos: self.pos
                        size: self.size
                        radius: [12]
                Label:
                    id: log_label
                    text: root.log_text
                    font_size: "11sp"
                    color: TEXT_WHITE
                    text_size: self.width - 16, None
                    size_hint_y: None
                    height: self.texture_size[1] + 16
                    padding: 8, 5
                    valign: 'top'

            BoxLayout:
                orientation: 'vertical'
                spacing: 6
                size_hint_y: 0.3

                Button:
                    text: "🌍 ESCANEAR + VALIDAR (REAL)"
                    font_size: "14sp"
                    bold: True
                    background_color: PRIMARY
                    color: 0, 0, 0, 1
                    on_release: root.start_real_scan()

                Button:
                    text: "📤 ENVIAR RESULTADOS A TELEGRAM"
                    font_size: "14sp"
                    bold: True
                    background_color: SUCCESS
                    color: 0, 0, 0, 1
                    on_release: root.send_results_to_telegram()

                Button:
                    text: "⚙️ CONFIGURACIÓN"
                    font_size: "13sp"
                    bold: True
                    background_color: get_color_from_hex("#444466")
                    color: TEXT_WHITE
                    on_release: app.root.current = 'settings'


<SettingsScreen>:
    name: 'settings'
    BoxLayout:
        orientation: 'vertical'
        canvas.before:
            Color:
                rgba: 0.039, 0, 0.122, 1
            Rectangle:
                pos: self.pos
                size: self.size

        BoxLayout:
            size_hint_y: 0.11
            padding: 12
            canvas.before:
                Color:
                    rgba: get_color_from_hex("#ff00ff")
                Rectangle:
                    pos: self.pos
                    size: self.size
            Label:
                text: "⚙️ CONFIGURACIÓN TELEGRAM"
                font_size: "17sp"
                bold: True
                color: 1, 1, 1, 1

        ScrollView:
            BoxLayout:
                orientation: 'vertical'
                padding: 14
                spacing: 5
                size_hint_y: None
                height: self.minimum_height

                Label:
                    text: "API ID"
                    color: PRIMARY
                    font_size: "12sp"
                TextInput:
                    id: api_id
                    text: root.api_id
                    multiline: False
                    background_color: INPUT_BG
                    foreground_color: TEXT_WHITE
                    size_hint_y: None
                    height: 36

                Label:
                    text: "API HASH"
                    color: PRIMARY
                    font_size: "12sp"
                TextInput:
                    id: api_hash
                    text: root.api_hash
                    multiline: False
                    background_color: INPUT_BG
                    foreground_color: TEXT_WHITE
                    size_hint_y: None
                    height: 36

                Label:
                    text: "Teléfono (+57...)"
                    color: PRIMARY
                    font_size: "12sp"
                TextInput:
                    id: phone
                    text: root.phone
                    multiline: False
                    background_color: INPUT_BG
                    foreground_color: TEXT_WHITE
                    size_hint_y: None
                    height: 36

                Label:
                    text: "Bot Token"
                    color: PRIMARY
                    font_size: "12sp"
                TextInput:
                    id: bot_token
                    text: root.bot_token
                    multiline: False
                    background_color: INPUT_BG
                    foreground_color: TEXT_WHITE
                    size_hint_y: None
                    height: 36

                Label:
                    text: "Chat ID"
                    color: PRIMARY
                    font_size: "12sp"
                TextInput:
                    id: chat_id
                    text: root.chat_id
                    multiline: False
                    background_color: INPUT_BG
                    foreground_color: TEXT_WHITE
                    size_hint_y: None
                    height: 36

        BoxLayout:
            size_hint_y: 0.13
            padding: 10
            spacing: 10

            Button:
                text: "💾 GUARDAR"
                font_size: "14sp"
                bold: True
                background_color: SUCCESS
                color: 0, 0, 0, 1
                on_release: root.save_credentials()

            Button:
                text: "⬅️ VOLVER"
                font_size: "14sp"
                bold: True
                background_color: get_color_from_hex("#444466")
                color: TEXT_WHITE
                on_release: app.root.current = 'main'
'''

# ==================== VALIDATION + LATIN DETECTION ====================
LATIN_AMERICAN_TIMEZONES = {"America/Bogota", "America/Mexico_City", "America/Sao_Paulo", "America/Lima", "America/Buenos_Aires", "America/Santiago"}

LATIN_KEYWORDS = {
    'es': ['univision', 'telemundo', 'caracol', 'rcn', 'azteca', 'canal 13', 'telefe', 'america tv', 'globo', 'sbt', 'record'],
    'pt': ['globo', 'sbt', 'record']
}

M3U_PATTERN = re.compile(r'https?://[^\s\'"]*get\.php\?username=[^&\s]+&password=[^&\s]+[^\s\'"]*', re.IGNORECASE)

async def validate_m3u_url(session, url, semaphore):
    async with semaphore:
        try:
            base = re.match(r'(https?://[^/]+)', url)
            if not base: return None
            base_url = base.group(1)

            user = re.search(r'username=([^&]+)', url)
            pw = re.search(r'password=([^&]+)', url)
            if not user or not pw: return None

            username = user.group(1)
            password = pw.group(1)

            api_url = f"{base_url}/player_api.php"
            async with session.get(api_url, params={'username': username, 'password': password}, timeout=8) as r:
                if r.status != 200: return None
                data = await r.json(content_type=None)
                if not isinstance(data, dict): return None

                ui = data.get('user_info', {})
                if ui.get('status') != 'Active': return None

                exp_str = "No especificada"
                days = None
                if ui.get('exp_date'):
                    try:
                        exp = datetime.fromtimestamp(int(ui['exp_date']))
                        days = (exp - datetime.now()).days
                        exp_str = exp.strftime('%Y-%m-%d')
                    except:
                        pass

                live_streams = []
                try:
                    async with session.get(api_url, params={'username': username, 'password': password, 'action': 'get_live_streams'}, timeout=7) as r2:
                        if r2.status == 200:
                            ld = await r2.json(content_type=None)
                            if isinstance(ld, list):
                                live_streams = ld[:40]
                except:
                    pass

                score = 0
                reasons = []
                tz = data.get('server_info', {}).get('timezone', '')
                if tz in LATIN_AMERICAN_TIMEZONES:
                    score += 35
                    reasons.append(f"Timezone: {tz}")

                cat_text = ' '.join(str(c.get('category_name', '')) for c in data.get('categories', [])).lower()
                ch_text = ' '.join(str(s.get('name', '')) for s in live_streams).lower()

                for lang, kws in LATIN_KEYWORDS.items():
                    for kw in kws:
                        if kw in cat_text or kw in ch_text:
                            score += 30
                            reasons.append(f"Keyword: {kw}")

                latin_score = min(100, int(score))

                return {
                    'url': url.strip(),
                    'username': username,
                    'password': password,
                    'status': ui.get('status'),
                    'exp_date': exp_str,
                    'days_left': days,
                    'connections': f"{ui.get('active_cons','N/A')}/{ui.get('max_connections','N/A')}",
                    'timezone': tz,
                    'channels': len(live_streams),
                    'latin_score': latin_score,
                    'is_latin': latin_score >= 48,
                    'latin_reasons': reasons[:4]
                }
        except:
            return None

# ==================== MAIN SCREEN ====================
class MainScreen(Screen):
    progress_value = NumericProperty(0)
    log_text = StringProperty("Listo para escanear de verdad...\n")
    status_text = StringProperty("✅ Credenciales cargadas")

    def __init__(self, **kwargs):
        super().__init__(**kwargs)
        Builder.load_string(KV)
        self.credentials = self.load_credentials()
        self.valid_results = []

    def load_credentials(self):
        store = JsonStore(os.path.join(App.get_running_app().user_data_dir, 'credentials.json'))
        if store.exists('telegram'):
            return store.get('telegram')
        return {}

    def update_log(self, message):
        self.log_text += message + "\n"

    def update_progress(self, value):
        self.progress_value = value

    def start_real_scan(self):
        if not self.credentials:
            self.update_log("❌ Ve a Configuración y guarda tus credenciales")
            return
        self.update_log("🚀 Escaneo REAL + Validación completa...")
        threading.Thread(target=self._run_full_scan, daemon=True).start()

    def _run_full_scan(self):
        try:
            from telethon import TelegramClient
            import aiohttp

            api_id = int(self.credentials.get('api_id'))
            api_hash = self.credentials.get('api_hash')
            phone = self.credentials.get('phone')

            loop = asyncio.new_event_loop()
            asyncio.set_event_loop(loop)
            client = TelegramClient('scanner_session', api_id, api_hash, loop=loop)

            async def scan():
                await client.start(phone=phone)
                self.update_log("✅ Conectado a Telegram")

                dialogs = await client.get_dialogs(limit=6)
                chat = dialogs[0]
                self.update_log(f"📡 Escaneando: {chat.name}")

                found_urls = set()
                async for msg in client.iter_messages(chat, limit=3500):
                    if msg.text:
                        found_urls.update(M3U_PATTERN.findall(msg.text))

                self.update_log(f"🔗 {len(found_urls)} URLs encontradas")

                semaphore = asyncio.Semaphore(20)
                results = []
                total = min(len(found_urls), 50)

                async with aiohttp.ClientSession() as session:
                    tasks = [validate_m3u_url(session, url, semaphore) for url in list(found_urls)[:total]]
                    for i, res in enumerate(await asyncio.gather(*tasks)):
                        self.update_progress((i / total) * 100)
                        if res:
                            results.append(res)

                self.valid_results = results
                latin = sum(1 for r in results if r.get('is_latin'))
                self.update_log(f"✅ {len(results)} válidas | {latin} LATINAS")
                self.update_progress(100)
                self.update_log("🎉 Escaneo completado")

            loop.run_until_complete(scan())
            client.disconnect()
        except Exception as e:
            self.update_log(f"❌ Error: {str(e)}")

    def send_results_to_telegram(self):
        if not self.valid_results:
            self.update_log("❌ No hay resultados para enviar")
            return
        self.update_log("📤 Enviando a Telegram...")
        threading.Thread(target=self._send_to_telegram, daemon=True).start()

    def _send_to_telegram(self):
        try:
            import aiohttp
            bot_token = self.credentials.get('bot_token')
            chat_id = self.credentials.get('chat_id')

            if not bot_token or not chat_id:
                self.update_log("❌ Falta Bot Token o Chat ID")
                return

            # Crear archivo temporal
            filepath = os.path.join(App.get_running_app().user_data_dir, "resultados.txt")
            with open(filepath, "w", encoding="utf-8") as f:
                f.write("=== RESULTADOS IPTV SUPER SCANNER v4.5 ===\n\n")
                for r in self.valid_results:
                    f.write(f"Host: {urlparse(r['url']).netloc}\n")
                    f.write(f"User: {r['username']} | Pass: {r['password']}\n")
                    f.write(f"Score LATAM: {r.get('latin_score')}% | Días: {r.get('days_left')}\n")
                    f.write(f"M3U: {r['url']}\n\n")

            async def send():
                url = f"https://api.telegram.org/bot{bot_token}/sendDocument"
                async with aiohttp.ClientSession() as session:
                    with open(filepath, 'rb') as f:
                        form = aiohttp.FormData()
                        form.add_field('chat_id', str(chat_id))
                        form.add_field('document', f, filename="Resultados_IPTV.txt")
                        form.add_field('caption', "✅ Resultados IPTV Super Scanner v4.5")
                        async with session.post(url, data=form, timeout=30) as r:
                            if r.status == 200:
                                self.update_log("✅ Archivo enviado a Telegram correctamente")
                            else:
                                self.update_log(f"❌ Error al enviar: {r.status}")

            loop = asyncio.new_event_loop()
            asyncio.set_event_loop(loop)
            loop.run_until_complete(send())
        except Exception as e:
            self.update_log(f"❌ Error enviando: {str(e)}")


# ==================== SETTINGS SCREEN ====================
class SettingsScreen(Screen):
    api_id = StringProperty("")
    api_hash = StringProperty("")
    phone = StringProperty("")
    bot_token = StringProperty("")
    chat_id = StringProperty("")

    def __init__(self, **kwargs):
        super().__init__(**kwargs)
        Builder.load_string(KV)
        self.load_credentials()

    def load_credentials(self):
        store = JsonStore(os.path.join(App.get_running_app().user_data_dir, 'credentials.json'))
        if store.exists('telegram'):
            data = store.get('telegram')
            self.api_id = data.get('api_id', '')
            self.api_hash = data.get('api_hash', '')
            self.phone = data.get('phone', '')
            self.bot_token = data.get('bot_token', '')
            self.chat_id = data.get('chat_id', '')

    def save_credentials(self):
        store = JsonStore(os.path.join(App.get_running_app().user_data_dir, 'credentials.json'))
        store.put('telegram',
                  api_id=self.ids.api_id.text,
                  api_hash=self.ids.api_hash.text,
                  phone=self.ids.phone.text,
                  bot_token=self.ids.bot_token.text,
                  chat_id=self.ids.chat_id.text)
        self.update_log_main("✅ Credenciales guardadas correctamente")

    def update_log_main(self, message):
        main_screen = self.manager.get_screen('main')
        main_screen.update_log(message)


class IPTVScannerApp(App):
    def build(self):
        self.title = "IPTV Super Scanner v4.5 (FINAL REAL)"
        sm = ScreenManager()
        sm.add_widget(MainScreen(name='main'))
        sm.add_widget(SettingsScreen(name='settings'))
        return sm

if __name__ == "__main__":
    IPTVScannerApp().run()
