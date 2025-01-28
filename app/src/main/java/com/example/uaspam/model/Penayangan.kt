package com.example.uaspam.model

import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
data class Penayangan (
    @PrimaryKey(autoGenerate = true)
    val id_penayangan: Int? = null,
    val id_studio: Int? = null,
    val id_film: Int? = null,
    val tanggal_penayangan: String,
    val harga_tiket: String
)

@Serializable
data class PenayanganResponseDetail(
    val status: Boolean,
    val message: String,
    val data: Penayangan
)

@Serializable
data class PenayanganResponse(
    val status: Boolean,
    val message: String,
    val data: List<Penayangan>
)