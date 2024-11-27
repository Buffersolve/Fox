package ua.fox.viewer.state

import androidx.compose.runtime.Stable

@Stable
sealed class ViewerUiInteract {
    data object OnBack : ViewerUiInteract()
}