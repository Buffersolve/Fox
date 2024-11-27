package ua.fox.viewer.state

import androidx.compose.runtime.Stable
import ua.fox.core.mvi.UiEvent
import ua.fox.data.model.FoxModel

@Stable
sealed class ViewerUiEvent : UiEvent {
    data class ScaleState(val scale: Float) : ViewerUiEvent()
}