package com.example.uaspam.ui.navigation


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
