package com.example.lab_jetpack_compose.ui.backend.ges_reservas

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lab_jetpack_compose.models.Reserva
import com.example.lab_jetpack_compose.repository.ReservaRepository
import kotlinx.coroutines.launch

class GesReservaViewModel(
    private val repo: ReservaRepository
) : ViewModel() {

    var reservas by mutableStateOf<List<Reserva>>(emptyList())
        private set

    init { load() }

    fun load() {
        viewModelScope.launch { reservas = repo.getAll() }
    }

    fun add(r: Reserva) {
        viewModelScope.launch { repo.add(r); load() }
    }

    fun update(r: Reserva) {
        viewModelScope.launch { repo.update(r); load() }
    }

    fun delete(id: Int) {
        viewModelScope.launch { repo.delete(id); load() }
    }
}
