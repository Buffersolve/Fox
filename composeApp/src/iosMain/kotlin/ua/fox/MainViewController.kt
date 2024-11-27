package ua.fox

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.window.ComposeUIViewController
import ua.fox.di.initKoin

fun MainViewController() = ComposeUIViewController {
    rememberSaveable { mutableStateOf(false) }.apply { if (!value) { initKoin(); value = true } }
    App()
}