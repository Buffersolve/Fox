package ua.fox.home.state

import androidx.compose.runtime.Stable
import ua.fox.data.model.FoxModel

@Stable
sealed class HomeUiInteract {
    data object OnPullToRefresh : HomeUiInteract()
    data class OnFoxClick(val fox: FoxModel) : HomeUiInteract()
}