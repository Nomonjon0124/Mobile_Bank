package com.ummug.mobilebank.domain.entity.History

data class HistoryRsponse(
    val `data`: List<Data>,
    val links: Links,
    val meta: Meta
)