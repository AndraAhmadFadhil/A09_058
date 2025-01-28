package com.example.uaspam.ui.view.penayangan

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uaspam.model.Film
import com.example.uaspam.model.Penayangan
import com.example.uaspam.model.Studio
import com.example.uaspam.ui.customwidget.CostumeTopAppBar
import com.example.uaspam.ui.navigation.DestinasiDetailPenayangan
import com.example.uaspam.ui.viewmodel.PenyediaViewModel
import com.example.uaspam.ui.viewmodel.penayangan.DetailPenayanganUiState
import com.example.uaspam.ui.viewmodel.penayangan.DetailPenayanganViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailPenayanganScreen(
    navigateBack: () -> Unit,
    onEditClick: () -> Unit,
    onBuyTicketClick: () -> Unit,
    modifier: Modifier = Modifier,
    @SuppressLint("NewApi") viewModel: DetailPenayanganViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    Scaffold(
        topBar = {
            CostumeTopAppBar(
                title = DestinasiDetailPenayangan.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefresh = {
                    viewModel.getPenayanganById()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onEditClick,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Mahasiswa"
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            DetailStatus(
                modifier = Modifier.weight(1f),
                detailPenayanganUiState = viewModel.detailPenayanganUiState,
                retryAction = { viewModel.getPenayanganById() }
            )
            Button(
                onClick = onBuyTicketClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = "Beli Tiket")
            }
        }
    }
}

@Composable
fun DetailStatus(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    detailPenayanganUiState: DetailPenayanganUiState,
    @SuppressLint("NewApi") viewModel: DetailPenayanganViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    when (detailPenayanganUiState) {
        is DetailPenayanganUiState.Loading -> com.example.uaspam.ui.view.penayangan.OnLoading(modifier = modifier.fillMaxSize())

        is DetailPenayanganUiState.Success -> {
            if (detailPenayanganUiState.penayangan.data.id_penayangan == 0) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Data tidak ditemukan.")
                }
            } else {
                ItemDetailPenayangan(
                    penayangan = detailPenayanganUiState.penayangan.data,
                    modifier = modifier.fillMaxWidth(),
                    filmList = viewModel.filmList,
                    studioList = viewModel.studioList
                )
            }
        }

        is DetailPenayanganUiState.Error -> com.example.uaspam.ui.view.penayangan.OnError(
            retryAction,
            modifier = modifier.fillMaxSize()
        )
    }
}

@Composable
fun ItemDetailPenayangan(
    modifier: Modifier = Modifier,
    penayangan: Penayangan,
    filmList: List<Film>,
    studioList: List<Studio>
) {
    val judulFilm = filmList.find { it.id_film == penayangan.id_film }?.judul_film ?: "Tidak ada Film"
    val namaStudio = studioList.find { it.id_studio == penayangan.id_studio }?.nama_studio ?: "Tidak ada Studio"
    Card(
        modifier = modifier.padding(16.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            DetailPenayangan(judul = "ID Penayangan", isinya = penayangan.id_penayangan.toString())
            DetailPenayangan(judul = "ID Film", isinya = penayangan.id_film.toString())
            DetailPenayangan(judul = "ID Studio", isinya = penayangan.id_studio.toString())
            DetailPenayangan(judul = "Tanggal Penayangan", isinya = penayangan.tanggal_penayangan)
            DetailPenayangan(judul = "Harga Tiket", isinya = penayangan.harga_tiket)

            DetailPenayangan(judul = "Nama Film", isinya = judulFilm)
            DetailPenayangan(judul = "Nama Studio", isinya = namaStudio)
        }
    }
}

@Composable
fun DetailPenayangan(
    modifier: Modifier = Modifier,
    judul: String,
    isinya: String,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "$judul : ",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )
        Text(
            text = isinya,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
