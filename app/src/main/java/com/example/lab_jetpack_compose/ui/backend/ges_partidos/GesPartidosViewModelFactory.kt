package com.example.lab_jetpack_compose.ui.backend.ges_partidos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.lab_jetpack_compose.repository.PartidoRepository

class GesPartidosViewModelFactory(
    private val repo: PartidoRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return GesPartidosViewModel(repo) as T
    }
}
