package com.ummug.mobilebank.domain.entity.cards

data class GetCardsesponse(
    val `data`: List<Data>,
    val links: Links,
    val meta: Meta
)