package com.example.uaspam.model

import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
data class Tiket (
    @PrimaryKey(autoGenerate = true)
    val id_tiket: Int? = null,
    val id_penayangan: Int? = null,
    val jumlah_tiket: Int = 0,
    val total_harga: String,
    val status_pembayaran: String,
)

@Serializable
data class TiketResponseDetail(
    val status: Boolean,
    val message: String,
    val data: Tiket
)

@Serializable
data class TiketResponse(
    val status: Boolean,
    val message: String,
    val data: List<Tiket>
)