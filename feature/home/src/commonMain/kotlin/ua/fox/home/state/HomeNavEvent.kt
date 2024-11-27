package ua.fox.home.state

import androidx.compose.runtime.Stable
import ua.fox.core.mvi.NavEvent
import ua.fox.data.model.FoxModel

@Stable
sealed class HomeNavEvent: NavEvent {
    data class GoViewerNavEvent(val fox: FoxModel) : HomeNavEvent()
}