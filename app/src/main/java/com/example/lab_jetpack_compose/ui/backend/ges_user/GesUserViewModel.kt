package com.example.lab_jetpack_compose.ui.backend.ges_user

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lab_jetpack_compose.models.User
import com.example.lab_jetpack_compose.repository.UserRepository
import kotlinx.coroutines.launch

class GesUserViewModel(
    // 👇 por defecto, usamos SIEMPRE el singleton DataUserRepository
    private val userRepository: UserRepository
) : ViewModel() {

    private var _users by mutableStateOf<List<User>>(emptyList())
    val users: List<User> get() = _users

    var selectedRole by mutableStateOf<String?>(null)
        private set

    init {
        loadUsers()
    }

    fun loadUsers() {
        viewModelScope.launch {
            _users = userRepository.getAllUsers()
        }
    }

    fun onRoleSelected(role: String?) {
        selectedRole = role
    }

    fun addUser(user: User) {
        viewModelScope.launch {
            userRepository.addUser(user)
            loadUsers()
        }
    }

    fun updateUser(user: User) {
        viewModelScope.launch {
            userRepository.updateUser(user)
            loadUsers()
        }
    }

    fun deleteUser(id: Int) {
        viewModelScope.launch {
            userRepository.deleteUser(id)
            loadUsers()
        }
    }
}
