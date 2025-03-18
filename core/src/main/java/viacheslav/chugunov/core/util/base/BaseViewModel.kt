package viacheslav.chugunov.core.util.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class BaseViewModel<State, Action>(initialState: State) : ViewModel() {
    private val mutableStateFlow = MutableStateFlow(initialState)
    val stateFlow = mutableStateFlow.asStateFlow()
    protected var state: State
        get() = mutableStateFlow.value
        set(value) { mutableStateFlow.value = value }

    abstract fun handleAction(action: Action)
}