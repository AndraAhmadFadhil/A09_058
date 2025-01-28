package com.example.uaspam.ui.navigation

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.uaspam.model.Penayangan
import com.example.uaspam.ui.view.HomeScreen
import com.example.uaspam.ui.view.film.DetailScreen
import com.example.uaspam.ui.view.film.FilmScreen
import com.example.uaspam.ui.view.film.InsertFilmView
import com.example.uaspam.ui.view.film.UpdateScreen
import com.example.uaspam.ui.view.penayangan.DetailPenayanganScreen
import com.example.uaspam.ui.view.penayangan.InsertPenayanganView
import com.example.uaspam.ui.view.penayangan.PenayanganScreen
import com.example.uaspam.ui.view.penayangan.UpdatePenayanganScreen
import com.example.uaspam.ui.view.studio.DetailStudioScreen
import com.example.uaspam.ui.view.studio.InsertStudioView
import com.example.uaspam.ui.view.studio.StudioScreen
import com.example.uaspam.ui.view.studio.UpdateStudioScreen
import com.example.uaspam.ui.view.tiket.DetailTiketScreen
import com.example.uaspam.ui.view.tiket.InsertTiketView
import com.example.uaspam.ui.view.tiket.TiketScreen
import com.example.uaspam.ui.view.tiket.UpdateTiketScreen

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun PengelolaHalaman(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = DestinasiBioskopHome.route,
        modifier = Modifier,
    ) {

        composable(
            DestinasiBioskopHome.route
        ) {
            HomeScreen(
                navigateToFilmEntry = { navController.navigate(DestinasiHomeFilm.route) },
                navigateToInsertFilm = { navController.navigate(DestinasiInsertFilm.route) },
                navigateToHomeStudio = { navController.navigate(DestinasiHomeStudio.route) },
                navigateToFilmList = {navController.navigate(DestinasiHomeFilm.route)},
                onCardClick = {navController.navigate(DestinasiHomeFilm.route)},
                navigateToPenayangan = { navController.navigate(DestinasiHomePenayangan.route) }
            )
        }
        composable(
            DestinasiDetailFilm.routeWithArgs,
            arguments = listOf(
                navArgument(DestinasiDetailFilm.ID_FILM) {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val id_film = backStackEntry.arguments?.getInt(DestinasiDetailFilm.ID_FILM)
            id_film?.let {
                DetailScreen(
                    navigateBack = {
                        navController.navigate(DestinasiHomeFilm.route) {
                            popUpTo(DestinasiBioskopHome.route) {
                                inclusive = true
                            }
                        }
                    },
                    onEditClick = {
                        navController.navigate("${DestinasiUpdateFilm.route}/$id_film")
                    },
                )
            }
        }
        composable(
            DestinasiUpdateFilm.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiUpdateFilm.ID_FILM) {
                    type = NavType.IntType
                }
            )
        ) {
            val id_film = it.arguments?.getInt(DestinasiUpdateFilm.ID_FILM)
            id_film?.let { id_film ->
                UpdateScreen(
                    onBack = {navController.popBackStack()},
                    onNavigate = {navController.popBackStack()}
                )
            }
        }
        composable(
            DestinasiInsertFilm.route
        ) {
            InsertFilmView(
                navigateBack = {
                    navController.navigate(DestinasiBioskopHome.route){
                        popUpTo(DestinasiBioskopHome.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(
            DestinasiHomeFilm.route
        ) {
            FilmScreen(
                navigateToFilmEntry = { navController.navigate(DestinasiHomeFilm.route) },
                onDetailClick = { id_film ->
                    navController.navigate("${DestinasiDetailFilm.route}/$id_film")
                },
                navigateBack = {
                    navController.navigate(DestinasiBioskopHome.route) }
            )
        }
        composable(
            DestinasiHomeStudio.route
        ) {
            StudioScreen(
                navigateToStudioEntry = { navController.navigate(DestinasiInsertStudio.route) },
                navigateToUpdate = { id_studio ->
                    navController.navigate("${DestinasiUpdateStudio.route}/$id_studio")
                },
                navigateBack = {
                    navController.navigate(DestinasiBioskopHome.route) },
                navigateToDetail = { id_studio ->
                    navController.navigate("${DestinasiDetailStudio.route}/$id_studio")
                }
            )
        }
        composable(
            DestinasiInsertStudio.route
        ) {
            InsertStudioView(
                navigateBack = {
                    navController.navigate(DestinasiHomeStudio.route){
                        popUpTo(DestinasiHomeStudio.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(
            DestinasiDetailStudio.routeWithArgs,
            arguments = listOf(
                navArgument(DestinasiDetailStudio.ID_STUDIO) {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val id_studio = backStackEntry.arguments?.getInt(DestinasiDetailStudio.ID_STUDIO)
            id_studio?.let {
                DetailStudioScreen(
                    navigateBack = {
                        navController.navigate(DestinasiHomeStudio.route) {
                            popUpTo(DestinasiHomeStudio.route) {
                                inclusive = true
                            }
                        }
                    },
                    onEditClick = {
                        navController.navigate("${DestinasiUpdateStudio.route}/$id_studio")
                    },
                )
            }
        }
        composable(
            DestinasiUpdateStudio.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiUpdateStudio.ID_STUDIO) {
                    type = NavType.IntType
                }
            )
        ) {
            val id_studio = it.arguments?.getInt(DestinasiUpdateStudio.ID_STUDIO)
            id_studio?.let { id_studio ->
                UpdateStudioScreen(
                    onBack = {navController.popBackStack()},
                    onNavigate = {navController.popBackStack()}
                )
            }
        }
        composable(
            DestinasiHomePenayangan.route
        ) {
            PenayanganScreen(
                navigateToPenayanganEntry = { navController.navigate(DestinasiInsertPenayangan.route) },
                navigateBack = {
                    navController.navigate(DestinasiBioskopHome.route) },
                onDetailClick = { id_penayangan ->
                    navController.navigate("${DestinasiDetailPenayangan.route}/$id_penayangan")
                },
                navigateToLihatTiket = {
                    navController.navigate(DestinasiHomeTiket.route)
                }
            )
        }
        composable(
            DestinasiInsertPenayangan.route
        ) {
            InsertPenayanganView(
                navigateBack = {
                    navController.navigate(DestinasiHomePenayangan.route){
                        popUpTo(DestinasiHomePenayangan.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(
            DestinasiDetailPenayangan.routeWithArgs,
            arguments = listOf(
                navArgument(DestinasiDetailPenayangan.ID_PENAYANGAN) {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val id_penayangan = backStackEntry.arguments?.getInt(DestinasiDetailPenayangan.ID_PENAYANGAN)
            id_penayangan?.let {
                DetailPenayanganScreen(
                    navigateBack = {
                        navController.navigate(DestinasiHomePenayangan.route) {
                            popUpTo(DestinasiHomePenayangan.route) {
                                inclusive = true
                            }
                        }
                    },
                    onEditClick = {
                        navController.navigate("${DestinasiUpdatePenayangan.route}/$id_penayangan")
                    },
                    onBuyTicketClick = {
                        navController.navigate("${DestinasiInsertTiket.route}/$id_penayangan")
                    }
                )
            }
        }
        composable(
            DestinasiUpdatePenayangan.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiUpdatePenayangan.ID_PENAYANGAN) {
                    type = NavType.IntType
                }
            )
        ) {
            val id_penayangan = it.arguments?.getInt(DestinasiUpdatePenayangan.ID_PENAYANGAN)
            id_penayangan?.let { id_penayangan ->
                UpdatePenayanganScreen(
                    onBack = {navController.popBackStack()},
                    onNavigate = {navController.popBackStack()}
                )
            }
        }

        //TIKET
        composable(
            DestinasiHomeTiket.route
        ) {
            TiketScreen(
                navigateToTiketEntry = { navController.navigate(DestinasiInsertTiket.route) },
                navigateBack = {
                    navController.navigate(DestinasiHomePenayangan.route) },
                onDetailClick = { id_tiket ->
                    navController.navigate("${DestinasiDetailTiket.route}/$id_tiket")
                }
            )
        }
        composable(
            "${DestinasiInsertTiket.route}/{id_penayangan}",
            arguments = listOf(
                navArgument("id_penayangan") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val id_penayangan = backStackEntry.arguments?.getInt("id_penayangan")
            id_penayangan?.let {
                InsertTiketView(
                    navigateBack = {
                        navController.popBackStack()
                    }
                )
            }
        }

        composable(
            DestinasiDetailTiket.routeWithArgs,
            arguments = listOf(
                navArgument(DestinasiDetailTiket.ID_TIKET) {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val id_tiket = backStackEntry.arguments?.getInt(DestinasiDetailTiket.ID_TIKET)
            id_tiket?.let {
                DetailTiketScreen(
                    navigateBack = {
                        navController.navigate(DestinasiHomeTiket.route) {
                            popUpTo(DestinasiHomeTiket.route) {
                                inclusive = true
                            }
                        }
                    },
                    onEditClick = {
                        navController.navigate("${DestinasiUpdateTiket.route}/$id_tiket")
                    },
                )
            }
        }
        composable(
            DestinasiUpdateTiket.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiUpdateTiket.ID_TIKET) {
                    type = NavType.IntType
                }
            )
        ) {
            val id_tiket = it.arguments?.getInt(DestinasiUpdateTiket.ID_TIKET)
            id_tiket?.let { id_tiket ->
                UpdateTiketScreen(
                    onBack = {navController.popBackStack()},
                    onNavigate = {navController.popBackStack()}
                )
            }
        }

    }
}
