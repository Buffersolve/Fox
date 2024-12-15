package ua.fox.home.ui

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ua.fox.core.mvi.BaseViewModel
import ua.fox.core.mvi.Reducer
import ua.fox.core.mvi.Reducer.EventWrapper
import ua.fox.data.network.repository.NetworkRepository
import ua.fox.data.network.service.ApiResult
import ua.fox.home.state.HomeNavEvent
import ua.fox.home.state.HomeUiEvent
import ua.fox.home.state.HomeUiInteract
import ua.fox.home.state.HomeUiState

class HomeViewModel : BaseViewModel<HomeUiState, HomeUiEvent, HomeNavEvent>(), KoinComponent {

    private val networkRepository: NetworkRepository by inject()

    /**
     * (MVI) Reducer for [HomeUiEvent], [HomeUiState] and [HomeNavEvent]
     */

    private val reducer = HomeReducer(HomeUiState())

    override val state: StateFlow<HomeUiState>
        get() = reducer.state

    private fun sendEvent(vararg event: HomeUiEvent) {
        event.forEach { reducer.sendEvent(it) }
    }

    override val navEvent: Flow<EventWrapper<HomeNavEvent>>
        get() = reducer.navEvent

    private fun sendNavEvent(vararg navEvent: HomeNavEvent) {
        navEvent.forEach { reducer.sendNavEvent(it) }
    }

    private class HomeReducer(initial: HomeUiState) : Reducer<HomeUiState, HomeUiEvent, HomeNavEvent>(initial) {
        override fun reduce(oldState: HomeUiState, event: HomeUiEvent) {
            when (event) {
                is HomeUiEvent.UpdateData -> setState(oldState.copy(data = event.data))
                is HomeUiEvent.ErrorState -> setState(oldState.copy(error = event.error))
                is HomeUiEvent.LoadingState -> setState(oldState.copy(isLoading = event.isLoading))
                is HomeUiEvent.RefreshState -> setState(oldState.copy(isRefreshing = event.isRefreshing))
            }
        }
    }

    /**
     *  Handle [HomeUiInteract] events
     */

    fun handleInteract(event: HomeUiInteract) {
        when (event) {
            is HomeUiInteract.OnPullToRefresh -> {
                sendEvent(HomeUiEvent.LoadingState(isLoading = true), HomeUiEvent.RefreshState(isRefreshing = true), HomeUiEvent.UpdateData(data = emptyList()))
                getFoxData()
            }
            is HomeUiInteract.OnFoxClick -> sendNavEvent(HomeNavEvent.GoViewerNavEvent(event.fox))
        }
    }

    /**
     *  Business Logic
     */

    private var foxJob: Job? = null

    init { getFoxData() }

    private fun getFoxData() {
        foxJob?.cancel()
        foxJob = viewModelScope.launch(Dispatchers.IO) {
            resetState()
            repeat(250) {
                when (val data = networkRepository.getFox()) {
                    is ApiResult.Success -> {
                        state.value.data.toMutableList().apply {
                            data.data?.let { fox -> add(fox) }
                            sendEvent(
                                HomeUiEvent.LoadingState(isLoading = false),
                                HomeUiEvent.RefreshState(isRefreshing = false),
                                HomeUiEvent.UpdateData(data = this)
                            )
                        }
                    }

                    is ApiResult.Error -> sendEvent(
                        HomeUiEvent.LoadingState(isLoading = false),
                        HomeUiEvent.RefreshState(isRefreshing = false),
                        HomeUiEvent.ErrorState(error = data.error)
                    )
                }

            }
        }
    }

    private fun resetState() = reducer.timeCapsule.apply { selectState(getStates().indexOf(getStates().first())) }

}