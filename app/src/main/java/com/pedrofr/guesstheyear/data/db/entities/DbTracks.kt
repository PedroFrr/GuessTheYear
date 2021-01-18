package com.pedrofr.guesstheyear.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "tracks_table")
data class DbTracks(
    @PrimaryKey val trackId: String = UUID.randomUUID().toString(),
    val title: String,
    val releaseYear: Int,
    val preview: String
)
