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
    // Recibe el repositorio por parámetro — no lo crea él, se lo pasa GesUserViewModelFactory
    // Así el ViewModel no depende de Room directamente, solo de la interfaz UserRepository
    private val userRepository: UserRepository
) : ViewModel() {

    // Lista de usuarios que se muestra en pantalla
    // mutableStateOf: Compose redibuja la UI automáticamente cuando cambia este valor
    private var _users by mutableStateOf<List<User>>(emptyList())
    val users: List<User> get() = _users

    // Rol seleccionado en los FilterChips — null significa 'Todos'
    var selectedRole by mutableStateOf<String?>(null)
        private set

    // Al crearse el ViewModel, carga los usuarios automáticamente
    init {
        loadUsers()
    }

    // Carga todos los usuarios de Room en el hilo de IO (viewModelScope usa Dispatchers.Main pero Room
    // redirige internamente al hilo de fondo). Actualiza _users → la UI se redibuja.
    fun loadUsers() {
        viewModelScope.launch {
            _users = userRepository.getAllUsers()
        }
    }

    // Cambia el filtro activo — null = sin filtro (muestra todos)
    fun onRoleSelected(role: String?) {
        selectedRole = role
    }

    // Inserta el usuario en Room y recarga la lista para que aparezca en pantalla
    fun addUser(user: User) {
        viewModelScope.launch {
            userRepository.addUser(user)
            loadUsers()
        }
    }

    // Actualiza el usuario en Room (busca por id) y recarga la lista
    fun updateUser(user: User) {
        viewModelScope.launch {
            userRepository.updateUser(user)
            loadUsers()
        }
    }

    // Borra el usuario por id y recarga la lista
    fun deleteUser(id: Int) {
        viewModelScope.launch {
            userRepository.deleteUser(id)
            loadUsers()
        }
    }
}
