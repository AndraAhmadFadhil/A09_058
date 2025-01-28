package com.example.uaspam.ui.view.tiket

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
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
import com.example.uaspam.model.Tiket
import com.example.uaspam.ui.customwidget.CostumeTopAppBar
import com.example.uaspam.ui.navigation.DestinasiDetailTiket
import com.example.uaspam.ui.view.penayangan.DetailPenayangan
import com.example.uaspam.ui.viewmodel.PenyediaViewModel
import com.example.uaspam.ui.viewmodel.penayangan.DetailPenayanganViewModel
import com.example.uaspam.ui.viewmodel.tiket.DetailTiketUiState
import com.example.uaspam.ui.viewmodel.tiket.DetailTiketViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailTiketScreen(
    navigateBack: () -> Unit,
    onEditClick: () -> Unit,
    modifier: Modifier = Modifier,
    @SuppressLint("NewApi") viewModel: DetailTiketViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    Scaffold(
        topBar = {
            CostumeTopAppBar(
                title = DestinasiDetailTiket.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefresh = {
                    viewModel.getTiketById()
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
        DetailStatus(
            modifier = Modifier.padding(innerPadding),
            detailTiketUiState = viewModel.detailTiketUiState,
            retryAction = { viewModel.getTiketById() }
        )
    }
}

@Composable
fun DetailStatus(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    detailTiketUiState: DetailTiketUiState,
    @SuppressLint("NewApi") viewModel: DetailTiketViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    when (detailTiketUiState) {
        is DetailTiketUiState.Loading -> com.example.uaspam.ui.view.OnLoading(modifier = modifier.fillMaxSize())

        is DetailTiketUiState.Success -> {
            if (detailTiketUiState.tiket.data.id_tiket == 0) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Data tidak ditemukan.")
                }
            } else {
                ItemDetailTiket(
                    tiket = detailTiketUiState.tiket.data,
                    modifier = modifier.fillMaxWidth(),
                    penayanganList = viewModel.penayanganList
                )
            }
        }

        is DetailTiketUiState.Error -> com.example.uaspam.ui.view.OnError(
            retryAction,
            modifier = modifier.fillMaxSize()
        )
    }
}

@Composable
fun ItemDetailTiket(
    modifier: Modifier = Modifier,
    tiket: Tiket,
    penayanganList: List<Penayangan>,
) {
    val tglPenayangan = penayanganList.find { it.id_penayangan == tiket.id_penayangan }?.tanggal_penayangan?: "Tidak ada Penayangan"
    Card(
        modifier = modifier.padding(16.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            DetailTiket(judul = "ID Tiket", isinya = tiket.id_tiket.toString())
            DetailTiket(judul = "ID Penayangan", isinya = tiket.id_penayangan.toString())
            DetailTiket(judul = "Jumlah Tiket", isinya = tiket.jumlah_tiket.toString())
            DetailTiket(judul = "Total Harga", isinya = tiket.total_harga)
            DetailTiket(judul = "Status Pembayaran", isinya = tiket.status_pembayaran)
            DetailTiket(judul = "Tanggal Penayangan", isinya = tglPenayangan)
        }
    }
}

@Composable
fun DetailTiket(
    modifier: Modifier = Modifier,
    judul: String,
    isinya: String,
){
    Column(modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start)
    {
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