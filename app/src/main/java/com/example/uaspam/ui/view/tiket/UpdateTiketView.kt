package com.example.uaspam.ui.view.tiket

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uaspam.ui.customwidget.CostumeTopAppBar
import com.example.uaspam.ui.navigation.DestinasiUpdateTiket
import com.example.uaspam.ui.view.film.EntryBody
import com.example.uaspam.ui.viewmodel.PenyediaViewModel
import com.example.uaspam.ui.viewmodel.tiket.UpdateTiketViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateTiketScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    onNavigate:()-> Unit,
    viewModel: UpdateTiketViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiUpdateTiket.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = onBack,
            )
        }
    ){padding ->
        EntryBodyTiket(
            modifier = Modifier.padding(padding),
            insertTiketUiState = viewModel.tiketUpdateUiState,
            onTiketValueChange = viewModel::updateInsertTiketState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateTiket()
                    delay(600)
                    withContext(Dispatchers.Main){
                        onNavigate()
                    }
                }
            },
            penayanganList = viewModel.penayanganList,
        )
    }
}