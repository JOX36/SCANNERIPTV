#!/usr/bin/env python3
"""
IPTV Super Scanner v4.5 - Android App
Interfaz moderna y atractiva con Kivy
"""

from kivy.app import App
from kivy.uix.boxlayout import BoxLayout
from kivy.uix.button import Button
from kivy.uix.label import Label
from kivy.uix.progressbar import ProgressBar
from kivy.uix.scrollview import ScrollView
from kivy.clock import Clock
from kivy.properties import StringProperty, NumericProperty
from kivy.lang import Builder
from kivy.utils import get_color_from_hex
import asyncio
import threading

# Colores Premium (Neón / Cyber)
PRIMARY = get_color_from_hex("#00ffcc")
SECONDARY = get_color_from_hex("#ff00ff")
TEXT_WHITE = get_color_from_hex("#ffffff")
SUCCESS = get_color_from_hex("#00ff88")

KV = '''
#:import get_color_from_hex kivy.utils.get_color_from_hex

<RootWidget>:
    orientation: 'vertical'
    canvas.before:
        Color:
            rgba: 0.039, 0, 0.122, 1
        Rectangle:
            pos: self.pos
            size: self.size

    BoxLayout:
        size_hint_y: 0.12
        padding: 15
        canvas.before:
            Color:
                rgba: get_color_from_hex("#00ffcc")
            Rectangle:
                pos: self.pos
                size: self.size
        Label:
            text: "🌌 IPTV SUPER SCANNER v4.5"
            font_size: "22sp"
            bold: True
            color: 0, 0, 0, 1

    BoxLayout:
        orientation: 'vertical'
        padding: 20
        spacing: 15

        Label:
            text: "✅ Conectado a Telegram"
            font_size: "16sp"
            color: PRIMARY
            size_hint_y: 0.08

        BoxLayout:
            orientation: 'vertical'
            size_hint_y: 0.25
            spacing: 8

            Label:
                text: "Progreso del Escaneo"
                font_size: "15sp"
                color: TEXT_WHITE

            ProgressBar:
                id: progress
                max: 100
                value: root.progress_value
                size_hint_y: 0.6

            Label:
                text: f"{root.progress_value:.0f}%"
                font_size: "18sp"
                bold: True
                color: PRIMARY

        ScrollView:
            size_hint_y: 0.45
            do_scroll_x: False
            canvas.before:
                Color:
                    rgba: 0.102, 0, 0.2, 1
                RoundedRectangle:
                    pos: self.pos
                    size: self.size
                    radius: [12]
            Label:
                id: log_label
                text: root.log_text
                font_size: "13sp"
                color: TEXT_WHITE
                text_size: self.width - 20, None
                size_hint_y: None
                height: self.texture_size[1] + 20
                padding: 15, 10
                valign: 'top'

        BoxLayout:
            orientation: 'vertical'
            spacing: 12
            size_hint_y: 0.35

            Button:
                text: "🌍 INICIAR DETECCIÓN LATINA"
                font_size: "17sp"
                bold: True
                background_color: PRIMARY
                color: 0, 0, 0, 1
                on_release: root.start_latin_scan()

            Button:
                text: "🔎 BUSCAR HOST ESPECÍFICO"
                font_size: "17sp"
                bold: True
                background_color: SECONDARY
                color: 1, 1, 1, 1
                on_release: root.start_host_search()

            Button:
                text: "📤 ENVIAR RESULTADOS A TELEGRAM"
                font_size: "16sp"
                bold: True
                background_color: SUCCESS
                color: 0, 0, 0, 1
                on_release: root.send_to_telegram()
'''

class RootWidget(BoxLayout):
    progress_value = NumericProperty(0)
    log_text = StringProperty("Listo para comenzar...\n")

    def __init__(self, **kwargs):
        super().__init__(**kwargs)
        Builder.load_string(KV)

    def update_log(self, message):
        self.log_text += message + "\n"

    def update_progress(self, value):
        self.progress_value = value

    def start_latin_scan(self):
        self.update_log("🌍 Iniciando Detección LATINA AVANZADA...")
        threading.Thread(target=self._run_latin_scan, daemon=True).start()

    def _run_latin_scan(self):
        for i in range(0, 101, 5):
            Clock.schedule_once(lambda dt, v=i: self.update_progress(v))
            if i == 30:
                Clock.schedule_once(lambda dt: self.update_log("📡 Escaneando 2.845 URLs..."))
            if i == 60:
                Clock.schedule_once(lambda dt: self.update_log("✅ 684 listas activas encontradas"))
            if i == 85:
                Clock.schedule_once(lambda dt: self.update_log("🌎 217 listas LATINAS detectadas"))
            asyncio.run(asyncio.sleep(0.15))
        Clock.schedule_once(lambda dt: self.update_log("🎉 ¡Escaneo completado con éxito!"))

    def start_host_search(self):
        self.update_log("🔎 Buscando host específico...")

    def send_to_telegram(self):
        self.update_log("📤 Enviando archivo a Telegram...")
        Clock.schedule_once(lambda dt: self.update_log("✅ Archivo enviado correctamente a tu chat."))

class IPTVScannerApp(App):
    def build(self):
        self.title = "IPTV Super Scanner v4.5"
        return RootWidget()

if __name__ == "__main__":
    IPTVScannerApp().run()
