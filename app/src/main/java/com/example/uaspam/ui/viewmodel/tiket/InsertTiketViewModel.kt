package com.example.uaspam.ui.viewmodel.tiket

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uaspam.model.Penayangan
import com.example.uaspam.model.Studio
import com.example.uaspam.model.Tiket
import com.example.uaspam.repo.FilmRepository
import com.example.uaspam.repo.PenayanganRepository
import com.example.uaspam.repo.StudioRepository
import com.example.uaspam.repo.TiketRepository
import kotlinx.coroutines.launch

class InsertTiketViewModel(
    private val tiket: TiketRepository,
    private val id_penayangan: PenayanganRepository,


    ): ViewModel(){

    var tiketUiState by mutableStateOf(InsertTiketUiState())
        private set

    var penayanganList by mutableStateOf<List<Penayangan>>(emptyList())

    init {
        loadPenayangan()
    }

    private fun loadPenayangan() {
        viewModelScope.launch {
            try {
                penayanganList = id_penayangan.getPenayangan().data
            } catch (e: Exception) {

            }
        }
    }

    fun updateInsertTiketState(insertTiketUiEvent: InsertTiketUiEvent){
        tiketUiState = InsertTiketUiState(insertTiketUiEvent = insertTiketUiEvent)
    }

    suspend fun insertTiket(){
        viewModelScope.launch {
            try {
                tiket.insertTiket(tiketUiState.insertTiketUiEvent.toTiket())
            } catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}

fun InsertTiketUiEvent.toTiket(): Tiket = Tiket(
    id_tiket = id_tiket ?: 0,
    id_penayangan = id_penayangan ?: 0,
    jumlah_tiket = jumlah_tiket,
    total_harga = total_harga,
    status_pembayaran = status_pembayaran,
)

fun Tiket.toInsertTiketUiEvent(): InsertTiketUiEvent = InsertTiketUiEvent(
    id_tiket = id_tiket ?: 0,
    id_penayangan = id_penayangan ?: 0,
    jumlah_tiket = jumlah_tiket,
    total_harga = total_harga,
    status_pembayaran = status_pembayaran,

)

fun Tiket.toUiStateTiket(): InsertTiketUiState = InsertTiketUiState(
    insertTiketUiEvent = toInsertTiketUiEvent()
)

data class InsertTiketUiState(
    val insertTiketUiEvent: InsertTiketUiEvent = InsertTiketUiEvent()
)

data class InsertTiketUiEvent(
    val id_tiket: Int? = null,
    val id_penayangan: Int? = null,
    val jumlah_tiket: Int = 0,
    val total_harga: String = "",
    val status_pembayaran: String = "",
)