package org.jox3.iptvscanner.data.models

data class IptvResult(
    val url: String,
    val username: String,
    val password: String,
    val status: String,
    val expDate: String,
    val daysLeft: Int?,
    val connections: String,
    val timezone: String,
    val channels: Int,
    val latinScore: Int,
    val isLatin: Boolean,
    val latinReasons: List<String> = emptyList()
)
