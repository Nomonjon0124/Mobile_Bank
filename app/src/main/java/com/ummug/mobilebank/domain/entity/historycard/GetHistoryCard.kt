package com.ummug.mobilebank.domain.entity.historycard

data class GetHistoryCard(
    val `data`: List<Data>,
    val links: Links,
    val meta: Meta
)