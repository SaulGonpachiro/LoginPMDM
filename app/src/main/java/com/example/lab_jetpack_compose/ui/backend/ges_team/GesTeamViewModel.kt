package com.example.lab_jetpack_compose.ui.backend.ges_team

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lab_jetpack_compose.models.Team
import com.example.lab_jetpack_compose.repository.TeamRepository
import kotlinx.coroutines.launch

class GesTeamViewModel(
    private val repo: TeamRepository
) : ViewModel() {

    var teams by mutableStateOf<List<Team>>(emptyList())
        private set

    init { load() }

    fun load() {
        viewModelScope.launch { teams = repo.getAll() }
    }

    fun add(t: Team) {
        viewModelScope.launch { repo.add(t); load() }
    }

    fun update(t: Team) {
        viewModelScope.launch { repo.update(t); load() }
    }

    fun delete(id: Int) {
        viewModelScope.launch { repo.delete(id); load() }
    }
}
