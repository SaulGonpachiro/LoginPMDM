package com.example.lab_jetpack_compose.ui.backend.ges_partidos

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lab_jetpack_compose.models.Partido
import com.example.lab_jetpack_compose.repository.PartidoRepository
import kotlinx.coroutines.launch

class GesPartidosViewModel(
    private val repo: PartidoRepository
) : ViewModel() {

    var partidos by mutableStateOf<List<Partido>>(emptyList())
        private set

    init { load() }

    fun load() {
        viewModelScope.launch { partidos = repo.getAll() }
    }

    fun add(p: Partido) {
        viewModelScope.launch { repo.add(p); load() }
    }

    fun update(p: Partido) {
        viewModelScope.launch { repo.update(p); load() }
    }

    fun delete(id: Int) {
        viewModelScope.launch { repo.delete(id); load() }
    }
}
