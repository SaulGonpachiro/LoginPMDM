package com.example.lab_jetpack_compose.ui.backend.ges_instalacion

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lab_jetpack_compose.models.Instalacion
import com.example.lab_jetpack_compose.repository.InstalacionRepository
import kotlinx.coroutines.launch

class GesInstalacionViewModel(
    private val repo: InstalacionRepository
) : ViewModel() {

    var instalaciones by mutableStateOf<List<Instalacion>>(emptyList())
        private set

    init { load() }

    fun load() {
        viewModelScope.launch { instalaciones = repo.getAll() }
    }

    fun add(inst: Instalacion) {
        viewModelScope.launch { repo.add(inst); load() }
    }

    fun update(inst: Instalacion) {
        viewModelScope.launch { repo.update(inst); load() }
    }

    fun delete(id: Int) {
        viewModelScope.launch { repo.delete(id); load() }
    }
}
