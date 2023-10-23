package com.ummug.mobilebank.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cards")
data class CardEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Int=0,
    val expire_month: Int,
    val expire_year: Int,
    val name: String,
    val pan: String
)