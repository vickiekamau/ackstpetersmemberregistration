package com.ack.stpeters.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ack.stpeters.model.FetchMembers
import com.ack.stpeters.model.MembersDao


@Database(entities = [FetchMembers::class], version = 3, exportSchema = false)
 abstract class AppDatabase: RoomDatabase() {
        abstract fun membersDao(): MembersDao
        companion object {
            private const val name = "AckSTPeters_database"


            @Volatile
            private var instance: AppDatabase? = null
            fun getDB(context: Context): AppDatabase {
                instance = Room.databaseBuilder(
                    context.applicationContext, AppDatabase::class.java,
                    name
                ).allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
                return instance!!
            }

        }

    }
