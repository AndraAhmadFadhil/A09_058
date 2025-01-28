package com.example.uaspam.model

import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
data class Studio (
    @PrimaryKey(autoGenerate = true)
    val id_studio: Int? = null,
    val nama_studio: String,
    val kapasitas: Int = 0,
)

@Serializable
data class StudioResponseDetail(
    val status: Boolean,
    val message: String,
    val data: Studio
)

@Serializable
data class StudioResponse(
    val status: Boolean,
    val message: String,
    val data: List<Studio>
)