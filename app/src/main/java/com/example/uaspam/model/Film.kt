package com.example.uaspam.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable


@Serializable
data class Film (
    @PrimaryKey(autoGenerate = true)
    val id_film: Int? = null,
    val judul_film: String,
    val durasi: Int = 0,
    val deskripsi: String,
    val genre: String,
    val rating_usia: String,
)

@Serializable
data class FilmResponseDetail(
    val status: Boolean,
    val message: String,
    val data: Film
)

@Serializable
data class FilmResponse(
    val status: Boolean,
    val message: String,
    val data: List<Film>
)
