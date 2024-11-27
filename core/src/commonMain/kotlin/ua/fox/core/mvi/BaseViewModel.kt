package ua.fox.core.mvi

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import ua.fox.core.mvi.Reducer.EventWrapper

abstract class BaseViewModel<T : UiState, in E : UiEvent, N : NavEvent> : ViewModel() {

    abstract val state: Flow<T>

    abstract val navEvent: Flow<EventWrapper<N>>

}