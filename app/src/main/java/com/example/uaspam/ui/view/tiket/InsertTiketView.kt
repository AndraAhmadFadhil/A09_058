package com.example.uaspam.ui.view.tiket

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uaspam.model.Penayangan
import com.example.uaspam.ui.customwidget.CostumeTopAppBar
import com.example.uaspam.ui.customwidget.Dropdown
import com.example.uaspam.ui.navigation.DestinasiInsertTiket
import com.example.uaspam.ui.viewmodel.PenyediaViewModel
import com.example.uaspam.ui.viewmodel.tiket.InsertTiketUiEvent
import com.example.uaspam.ui.viewmodel.tiket.InsertTiketUiState
import com.example.uaspam.ui.viewmodel.tiket.InsertTiketViewModel
import kotlinx.coroutines.launch

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertTiketView(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertTiketViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiInsertTiket.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        EntryBodyTiket(
            insertTiketUiState = viewModel.tiketUiState,
            onTiketValueChange = viewModel::updateInsertTiketState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertTiket()
                    navigateBack()
                }
            },
            penayanganList = viewModel.penayanganList,
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun EntryBodyTiket(
    insertTiketUiState: InsertTiketUiState,
    onTiketValueChange: (InsertTiketUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier,
    penayanganList: List<Penayangan>
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInputTiket(
            insertTiketUiEvent = insertTiketUiState.insertTiketUiEvent,
            onValueChange = onTiketValueChange,
            modifier = Modifier.fillMaxWidth(),
            penayanganList = penayanganList,
            insertTiketUiState = insertTiketUiState
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Simpan")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInputTiket(
    insertTiketUiEvent: InsertTiketUiEvent,
    insertTiketUiState: InsertTiketUiState,
    modifier: Modifier = Modifier,
    onValueChange: (InsertTiketUiEvent) -> Unit = {},
    enabled: Boolean = true,
    penayanganList: List<Penayangan>
) {
    val hargaTiket = 50000  //harga
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Dropdown(
            selectedValue = penayanganList.find { it.id_penayangan == insertTiketUiState.insertTiketUiEvent.id_penayangan }?.tanggal_penayangan ?: "",
            options = penayanganList.map { it.tanggal_penayangan },
            label = "Pilih Penayangan",
            onValueChangedEvent = { judul ->
                val pilihPenayangan = penayanganList.firstOrNull { it.tanggal_penayangan == judul }
                pilihPenayangan?.let {
                    onValueChange(insertTiketUiState.insertTiketUiEvent.copy(id_penayangan = it.id_penayangan))
                }
            }
        )
        OutlinedTextField(
            value = insertTiketUiEvent.jumlah_tiket.toString(),
            onValueChange = {
                val jumlahTiket = it.toIntOrNull() ?: 0
                val totalHarga = jumlahTiket * hargaTiket
                onValueChange(
                    insertTiketUiEvent.copy(
                        jumlah_tiket = jumlahTiket,
                        total_harga = totalHarga.toString()
                    )
                )
            },
            label = { Text("Jumlah Tiket") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertTiketUiEvent.total_harga,
            onValueChange = { },
            label = { Text("Total Harga") },
            modifier = Modifier.fillMaxWidth(),
            enabled = false,
            singleLine = true
        )
        OutlinedTextField(
            value = insertTiketUiEvent.status_pembayaran,
            onValueChange = { onValueChange(insertTiketUiEvent.copy(status_pembayaran = it)) },
            label = { Text("Status Pembayaran") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        if (enabled) {
            Text(
                text = "Data Tidak Boleh Kosong!",
                modifier = Modifier.padding(12.dp)
            )
        }
    }
}
