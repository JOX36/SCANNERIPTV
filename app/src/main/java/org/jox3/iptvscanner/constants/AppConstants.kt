package org.jox3.iptvscanner.constants

object AppConstants {
    const val APP_NAME = "IPTV Super Scanner"
    const val VERSION = "4.5"

    // Límites recomendados para no sobrecargar
    const val MAX_MESSAGES_TO_SCAN = 4000
    const val MAX_URLS_TO_VALIDATE = 60

    // Umbral para considerar una lista como LATINA
    const val LATIN_SCORE_THRESHOLD = 48
}
