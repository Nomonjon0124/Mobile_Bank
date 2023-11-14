package com.ummug.mobilebank.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.ummug.mobilebank.domain.entity.cards.Data

@Dao
interface DataDao {

    @Insert
    fun insert(contact: Data)

    @Insert
    fun insertAll(contacts: List<Data>)

    @Query("SELECT * FROM data")
    fun getCards():List<Data>

    @Delete
    fun deletecard(contact:Data)

    @Update
    fun updatecard(contact: Data)

    @Query("DELETE FROM data")
    fun nukeTable()

}