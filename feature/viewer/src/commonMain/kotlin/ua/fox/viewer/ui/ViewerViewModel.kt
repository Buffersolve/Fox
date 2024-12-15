package ua.fox.viewer.ui

import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import ua.fox.core.mvi.BaseViewModel
import ua.fox.core.mvi.Reducer
import ua.fox.navigation.Viewer
import ua.fox.viewer.state.ViewerNavEvent
import ua.fox.viewer.state.ViewerUiEvent
import ua.fox.viewer.state.ViewerUiInteract
import ua.fox.viewer.state.ViewerUiState

class ViewerViewModel(savedStateHandle: SavedStateHandle) : BaseViewModel<ViewerUiState, ViewerUiEvent, ViewerNavEvent>() {

    private val fox = Viewer.from(savedStateHandle).fox

    /**
     * (MVI) Reducer for [ViewerUiState], [ViewerUiEvent] and [ViewerNavEvent]
     */

    private val reducer = ViewerReducer(ViewerUiState(fox))

    override val state: StateFlow<ViewerUiState>
        get() = reducer.state

    /*private fun sendEvent(vararg event: ViewerUiEvent) {
        event.forEach { reducer.sendEvent(it) }
    }*/

    override val navEvent: Flow<Reducer.EventWrapper<ViewerNavEvent>>
        get() = reducer.navEvent

    private fun sendNavEvent(vararg navEvent: ViewerNavEvent) {
        navEvent.forEach {reducer.sendNavEvent(it) }
    }

    private class ViewerReducer(initial: ViewerUiState) : Reducer<ViewerUiState, ViewerUiEvent, ViewerNavEvent>(initial) {
        override fun reduce(oldState: ViewerUiState, event: ViewerUiEvent) { /*EMPTY*/ }
    }

    /**
     *  Handle [ViewerUiInteract] events
     */

    fun handleInteract(event: ViewerUiInteract) {
        when (event) {
            is ViewerUiInteract.OnBack -> sendNavEvent(ViewerNavEvent.GoBackNavEvent)
        }
    }

}