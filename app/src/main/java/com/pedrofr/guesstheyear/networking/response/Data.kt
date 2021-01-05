package com.pedrofr.guesstheyear.networking.response

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.serialization.Serializable

@Entity
data class Data(
    @PrimaryKey var id: String,
    @field:Json(name = "sport_key") var name: String?,
    var picture: String?,
    var picture_big: String?,
    var picture_medium: String?,
    var picture_small: String?,
    var picture_xl: String?,
    var type: String?
)