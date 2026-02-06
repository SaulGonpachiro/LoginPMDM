package com.example.lab_jetpack_compose.ui.backend.ges_team

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.lab_jetpack_compose.repository.TeamRepository

class GesTeamViewModelFactory(
    private val repo: TeamRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return GesTeamViewModel(repo) as T
    }
}
