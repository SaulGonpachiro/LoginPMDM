package com.example.lab_jetpack_compose.ui.backend.ges_instalacion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.lab_jetpack_compose.repository.InstalacionRepository

class GesInstalacionViewModelFactory(
    private val repo: InstalacionRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return GesInstalacionViewModel(repo) as T
    }
}
