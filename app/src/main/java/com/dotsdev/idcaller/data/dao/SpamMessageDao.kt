package com.dotsdev.idcaller.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.dotsdev.idcaller.data.model.Message

@Dao
interface SpamMessageDao {
    @Insert
    suspend fun insert(message: Message?)

    @Query("DELETE FROM Message")
    fun deleteAll()

    @Query("DELETE FROM Message WHERE Message.uniqueId = :id")
    fun deleteMessage(id: Int)

    @get:Query("SELECT * FROM Message ORDER BY uniqueId ASC")
    val messages: LiveData<List<Message?>?>?

    @Update
    suspend fun update(vararg Message: Message?)

    @Query("SELECT * FROM Message WHERE Message.uniqueId = :id LIMIT 1")
    suspend fun getMessageByIdNote(id: Int): Message?
}