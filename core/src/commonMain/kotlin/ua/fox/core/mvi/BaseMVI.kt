package ua.fox.core.mvi

import io.ktor.util.date.getTimeMillis
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*

abstract class Reducer<S : UiState, E : UiEvent, N: NavEvent>(initialVal: S) {

    private val _state: MutableStateFlow<S> = MutableStateFlow(initialVal)
    val state: StateFlow<S> = _state.asStateFlow()

    val timeCapsule: TimeCapsule<S> = TimeTravelCapsule { storedState ->
        _state.tryEmit(storedState)
    }

    init { timeCapsule.addState(initialVal) }

    fun sendEvent(event: E) {
        reduce(oldState = _state.value, event = event)
    }

    fun setState(newState: S) {
        val success = _state.tryEmit(newState)
        if (success) timeCapsule.addState(newState)
    }

    abstract fun reduce(oldState: S, event: E)

    // Navigation
    data class EventWrapper<out N>(val event: N, val timestamp: Long = getTimeMillis())

    private val _navEvent = Channel<EventWrapper<N>>()
    val navEvent = _navEvent.receiveAsFlow()

    private fun <N> Channel<EventWrapper<N>>.sendNavEvent(element: N) = trySend(EventWrapper(element))

    fun sendNavEvent(navEvent: N) {
        _navEvent.sendNavEvent(navEvent)
    }

}

interface UiState

interface UiEvent

interface NavEvent