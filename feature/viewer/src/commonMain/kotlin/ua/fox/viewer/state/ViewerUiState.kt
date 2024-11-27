package ua.fox.viewer.state

import androidx.compose.runtime.Immutable
import ua.fox.core.mvi.UiState
import ua.fox.data.model.FoxModel

@Immutable
data class ViewerUiState(
    val fox: FoxModel,
) : UiState