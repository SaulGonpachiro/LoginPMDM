package com.example.lab_jetpack_compose.ui.backend.ges_reservas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.lab_jetpack_compose.repository.ReservaRepository

class GesReservaViewModelFactory(
    private val repo: ReservaRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return GesReservaViewModel(repo) as T
    }
}
