package com.example.uaspam.ui.view.studio

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
import com.example.uaspam.ui.customwidget.CostumeTopAppBar
import com.example.uaspam.ui.navigation.DestinasiInsertStudio
import com.example.uaspam.ui.viewmodel.PenyediaViewModel
import com.example.uaspam.ui.viewmodel.studio.InsertStudioUiEvent
import com.example.uaspam.ui.viewmodel.studio.InsertStudioUiState
import com.example.uaspam.ui.viewmodel.studio.InsertStudioViewModel
import kotlinx.coroutines.launch


@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertStudioView(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertStudioViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiInsertStudio.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) {innerPadding ->
        EntryBodyStudio(
            insertStudioUiState = viewModel.studioUiState,
            onStudioValueChange = viewModel::updateInsertStudioState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertStudio()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )

    }
}

@Composable
fun EntryBodyStudio(
    insertStudioUiState: InsertStudioUiState,
    onStudioValueChange: (InsertStudioUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInput(
            insertStudioUiEvent = insertStudioUiState.insertStudioUiEvent,
            onValueChange = onStudioValueChange,
            modifier = Modifier.fillMaxWidth())
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
fun FormInput(
    insertStudioUiEvent: InsertStudioUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertStudioUiEvent) -> Unit = {},
    enabled: Boolean = true
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = insertStudioUiEvent.nama_studio,
            onValueChange = {onValueChange(insertStudioUiEvent.copy(nama_studio = it))},
            label = { Text("Nama Studio") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertStudioUiEvent.kapasitas.toString(),
            onValueChange = {onValueChange(insertStudioUiEvent.copy(kapasitas = it.toInt()))},
            label = { Text("Kapasitas") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

    }
}