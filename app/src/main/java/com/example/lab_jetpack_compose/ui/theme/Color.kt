package com.example.lab_jetpack_compose.ui.theme

import androidx.compose.ui.graphics.Color

// ─────────────────────────────────────────────────────────────────────────
//  COLORES ORIGINALES DE MATERIAL THEME — requeridos por Theme.kt
//  No borrar: Theme.kt los usa para configurar el ColorScheme de la app
// ─────────────────────────────────────────────────────────────────────────
val Purple80      = Color(0xFFD0BCFF)
val PurpleGrey80  = Color(0xFFCCC2DC)
val Pink80        = Color(0xFFEFB8C8)

val Purple40      = Color(0xFF6650a4)
val PurpleGrey40  = Color(0xFF625b71)
val Pink40        = Color(0xFF7D5260)

// ─────────────────────────────────────────────────────────────────────────
//  COLORES DEL SISTEMA DE DISEÑO - GES SPORT
// ─────────────────────────────────────────────────────────────────────────

// Colores base de la app (login, home, botones principales)
val CustomRed            = Color(0xFFB40900)   // Rojo principal — topbar, títulos, acentos
val PrimaryRed           = Color(0xFF8F0700)   // Rojo oscuro — botones de acción primaria
val SemiTransparentWhite = Color(0x34FFFFFF)   // Blanco semitransparente — fondos de paneles

// Colores del backoffice (pantallas de gestión del admin)
val OverlayDark          = Color(0xAA000000)   // Negro semitransparente — capa sobre imagen de fondo
val CardDark             = Color(0xFF111827)   // Gris muy oscuro — fondo de cards en gestión
val AccentPurple         = Color(0xFF8B5CF6)   // Morado — FAB y botones de confirmación
val AccentRed            = Color(0xFFEF4444)   // Rojo vivo — botones de borrado/peligro

// Texto
val TextMuted            = Color(0xFF9CA3AF)   // Gris claro — texto secundario / subtítulos
