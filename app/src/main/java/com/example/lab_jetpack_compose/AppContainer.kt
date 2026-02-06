package com.example.lab_jetpack_compose

import android.content.Context
import com.example.lab_jetpack_compose.data.RoomUserRepository
import com.example.lab_jetpack_compose.database.AppDatabase
import com.example.lab_jetpack_compose.repository.UserRepository
import com.example.lab_jetpack_compose.data.RoomInstalacionRepository
import com.example.lab_jetpack_compose.repository.InstalacionRepository
import com.example.lab_jetpack_compose.data.RoomPartidoRepository
import com.example.lab_jetpack_compose.repository.PartidoRepository
import com.example.lab_jetpack_compose.data.RoomReservaRepository
import com.example.lab_jetpack_compose.repository.ReservaRepository
import com.example.lab_jetpack_compose.data.RoomTeamRepository
import com.example.lab_jetpack_compose.repository.TeamRepository



class AppContainer(context: Context) {
    private val db = AppDatabase.getDatabase(context)

    val userRepository: UserRepository = RoomUserRepository(db.userDao())
    val instalacionRepository: InstalacionRepository = RoomInstalacionRepository(db.instalacionDao())

    val partidoRepository: PartidoRepository = RoomPartidoRepository(db.partidoDao())
    val reservaRepository: ReservaRepository = RoomReservaRepository(db.reservaDao())
    val teamRepository: TeamRepository = RoomTeamRepository(db.teamDao())

}
