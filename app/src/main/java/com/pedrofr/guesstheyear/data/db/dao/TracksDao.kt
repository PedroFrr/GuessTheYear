package com.pedrofr.guesstheyear.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.pedrofr.guesstheyear.data.db.entities.DbTracks

@Dao
interface TracksDao {

    @Query("SELECT * FROM tracks_table")
    suspend fun fetchTracks(): List<DbTracks>

}