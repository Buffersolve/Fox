package ua.fox.viewer.state

import androidx.compose.runtime.Stable
import ua.fox.core.mvi.NavEvent
import ua.fox.data.model.FoxModel

@Stable
sealed class ViewerNavEvent: NavEvent {
    data object GoBackNavEvent : ViewerNavEvent()
}