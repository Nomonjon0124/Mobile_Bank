package com.ummug.mobilebank.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ummug.mobilebank.domain.entity.cards.Data

@Database(entities = [Data::class], version = 1)

abstract class AppDatabase:RoomDatabase() {

    abstract fun contactDao():DataDao
}

class Database{

    companion object{
        private lateinit var appDatabase: AppDatabase

        fun getDatabase(context: Context):AppDatabase{
            if (!::appDatabase.isInitialized){
                appDatabase= Room
                    .databaseBuilder(context,AppDatabase::class.java,"database")
                    .allowMainThreadQueries()
                    .build()
            }
            return appDatabase
        }
    }
}