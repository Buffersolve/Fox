package ua.fox.home.state

import androidx.compose.runtime.Immutable
import ua.fox.core.mvi.UiState
import ua.fox.data.model.FoxModel

@Immutable
data class HomeUiState(
    val isLoading: Boolean = true,
    val isRefreshing: Boolean = false,
    val data: List<FoxModel> = emptyList(),
    val error: String? = null
) : UiState