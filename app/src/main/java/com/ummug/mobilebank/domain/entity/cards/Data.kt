package com.ummug.mobilebank.domain.entity.cards

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "data")
data class Data(
    @ColumnInfo("amount")
    val amount: String,
    @ColumnInfo("month")
    val expire_month: Int,
    @ColumnInfo("year")
    val expire_year: Int,
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo("name")
    val name: String,
    @ColumnInfo("pan")
    val pan: String,
    @ColumnInfo("phone_number")
    val phone_number: String,
    @ColumnInfo("theme")
    val theme: Int
):Serializable