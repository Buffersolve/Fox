package ua.fox.home.state

import androidx.compose.runtime.Stable
import ua.fox.core.mvi.UiEvent
import ua.fox.data.model.FoxModel

@Stable
sealed class HomeUiEvent : UiEvent {
    data class UpdateData(val data: List<FoxModel>) : HomeUiEvent()
    data class ErrorState(val error: String?) : HomeUiEvent()
    data class LoadingState(val isLoading: Boolean) : HomeUiEvent()
    data class RefreshState(val isRefreshing: Boolean) : HomeUiEvent()
}