package com.example.uaspam.ui.navigation

import com.example.uaspam.ui.navigation.DestinasiDetailPenayangan.ID_PENAYANGAN


interface DestinasiNavigasi {
    val route: String
    val titleRes: String
}

object DestinasiBioskopHome: DestinasiNavigasi {
    override val route = "Halaman Utama"
    override val titleRes = "Home Bioskop App"
}

//FILM
object DestinasiHomeFilm : DestinasiNavigasi{
    override val route = "Home Film"
    override val titleRes = "Manajemen Home Film"
}

object DestinasiInsertFilm : DestinasiNavigasi{
    override val route = "Insert Film"
    override val titleRes = "Manajemen Insert Film"
}

object DestinasiUpdateFilm : DestinasiNavigasi{
    override val route = "Update Film"
    override val titleRes = "Manajemen Update Film"
    const val ID_FILM = "id_film"
    val routesWithArg = "${route}/{$ID_FILM}"
}

object DestinasiDetailFilm : DestinasiNavigasi{
    override val route = "Detail Film"
    override val titleRes = "Manajemen Detail Film"
    const val ID_FILM = "id_film"
    val routeWithArgs = "$route/{$ID_FILM}"
}

//STUDIO
object DestinasiHomeStudio : DestinasiNavigasi{
    override val route = "Home Studio"
    override val titleRes = "Manajemen Home Studio"
}

object DestinasiInsertStudio : DestinasiNavigasi{
    override val route = "Insert Studio"
    override val titleRes = "Manajemen Insert Studio"
}

object DestinasiUpdateStudio : DestinasiNavigasi{
    override val route = "Update Studio"
    override val titleRes = "Manajemen Update Studio"
    const val ID_STUDIO = "id_studio"
    val routesWithArg = "${route}/{$ID_STUDIO}"
}

object DestinasiDetailStudio : DestinasiNavigasi{
    override val route = "Detail Studio"
    override val titleRes = "Manajemen Detail Studio"
    const val ID_STUDIO = "id_studio"
    val routeWithArgs = "$route/{$ID_STUDIO}"
}

//PENAYANGAN
object DestinasiHomePenayangan : DestinasiNavigasi{
    override val route = "Home Penayangan"
    override val titleRes = "Manajemen Home Penayangan"
}

object DestinasiInsertPenayangan : DestinasiNavigasi{
    override val route = "Insert Penayangan"
    override val titleRes = "Manajemen Insert Penayangan"
}

object DestinasiUpdatePenayangan : DestinasiNavigasi{
    override val route = "Update Penayangan"
    override val titleRes = "Manajemen Update Penayangan"
    const val ID_PENAYANGAN = "id_penayangan"
    val routesWithArg = "${route}/{$ID_PENAYANGAN}"
}

object DestinasiDetailPenayangan : DestinasiNavigasi{
    override val route = "Detail Penayangan"
    override val titleRes = "Manajemen Detail Penayangan"
    const val ID_PENAYANGAN = "id_penayangan"
    val routeWithArgs = "$route/{$ID_PENAYANGAN}"
}

//TIKET
object DestinasiHomeTiket : DestinasiNavigasi{
    override val route = "Home Tiket"
    override val titleRes = "Manajemen Home Tiket"
}

object DestinasiInsertTiket : DestinasiNavigasi{
    override val route = "Insert Tiket"
    override val titleRes = "Manajemen Insert Tiket"
    val routeWithArgs = "$route/{$ID_PENAYANGAN}"

}

object DestinasiUpdateTiket : DestinasiNavigasi{
    override val route = "Update Tiket"
    override val titleRes = "Manajemen Update Tiket"
    const val ID_TIKET = "id_tiket"
    val routesWithArg = "${route}/{$ID_TIKET}"
}

object DestinasiDetailTiket : DestinasiNavigasi{
    override val route = "Detail Tiket"
    override val titleRes = "Manajemen Detail Tiket"
    const val ID_TIKET = "id_tiket"
    val routeWithArgs = "$route/{$ID_TIKET}"
}