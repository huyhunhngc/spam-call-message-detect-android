package com.dotsdev.idcaller.data.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dotsdev.idcaller.data.converter.ContactConverter
import com.dotsdev.idcaller.data.converter.DateConverter
import com.dotsdev.idcaller.data.model.Message

@Database(entities = [Message::class], version = 1)
@TypeConverters(DateConverter::class, ContactConverter::class)
abstract class RoomRepository : RoomDatabase() {
    abstract fun spamMessageDao(): SpamMessageDao

    companion object {
        private var INSTANCE: RoomRepository? = null
        private const val DB_NAME = "db_idcaller.db"

        fun getDatabase(context: Context): RoomRepository {
            if (INSTANCE == null) {
                synchronized(RoomRepository::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            RoomRepository::class.java,
                            DB_NAME
                        )
                            .allowMainThreadQueries()
                            .build()
                    }
                }
            }
            return INSTANCE!!
        }
    }
}
