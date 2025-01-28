package com.example.uaspam.ui.view.tiket

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uaspam.model.Tiket
import com.example.uaspam.ui.customwidget.CostumeTopAppBar
import com.example.uaspam.ui.navigation.DestinasiHomeTiket
import com.example.uaspam.ui.navigation.DestinasiNavigasi
import com.example.uaspam.ui.viewmodel.PenyediaViewModel
import com.example.uaspam.ui.viewmodel.tiket.HomeTiketUiState
import com.example.uaspam.ui.viewmodel.tiket.HomeTiketViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TiketScreen(
    navigateToTiketEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (Int) -> Unit = {},
    navigateBack: () -> Unit,
    @SuppressLint("NewApi") viewModel: HomeTiketViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {

    Scaffold(
        topBar = {
            CostumeTopAppBar(
                title = DestinasiHomeTiket.titleRes,
                navigateUp = navigateBack,
                canNavigateBack = true,
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = navigateToTiketEntry) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Add Tiket")
            }
        },
    ) { innerPadding ->
        TiketStatus(
            tiketUiState = viewModel.tiketUIState,
            retryAction = { viewModel.getTiket() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,
            onDeleteClick = {
                viewModel.deleteTiket(it.id_tiket ?: 0)
                viewModel.getTiket()
            }
        )
    }
}

@Composable
fun TiketStatus(
    tiketUiState: HomeTiketUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (Int) -> Unit,
    onDeleteClick: (Tiket) -> Unit
) {
    when (tiketUiState) {
        is HomeTiketUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is HomeTiketUiState.Success ->
            if (tiketUiState.tiket.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data Tiket")
                }
            } else {
                TiketLayout(
                    tikets = tiketUiState.tiket,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = { onDetailClick (it.id_tiket ?: 0) },
                    onDeleteClick = onDeleteClick
                )
            }
        is HomeTiketUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun OnLoading(modifier: Modifier = Modifier) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun OnError(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Terjadi kesalahan", modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text("Coba Lagi")
        }
    }
}

@Composable
fun TiketLayout(
    tikets: List<Tiket>,
    modifier: Modifier = Modifier,
    onDetailClick: (Tiket) -> Unit,
    onDeleteClick: (Tiket) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(tikets) { tiket ->
            TiketCard(
                tiket = tiket,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(tiket) },
                onDeleteClick = { onDeleteClick(tiket) }
            )
        }
    }
}


@Composable
fun TiketCard(
    tiket: Tiket,
    modifier: Modifier = Modifier,
    onDeleteClick: (Tiket) -> Unit
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "ID Penayangan: ${tiket.id_penayangan}", style = MaterialTheme.typography.titleLarge)
            Text(text = "Jumlah Tiket: ${tiket.jumlah_tiket} tiket", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Total Harga: ${tiket.total_harga}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Status Pembayaran: ${tiket.status_pembayaran}", style = MaterialTheme.typography.bodyMedium)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {

                IconButton(onClick = { onDeleteClick(tiket) }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete Tiket")
                }
            }
        }
    }
}
