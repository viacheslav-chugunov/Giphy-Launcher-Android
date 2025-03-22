package viacheslav.chugunov.core.util.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

abstract class BaseViewModel<State, Action>(initialState: State) : ViewModel() {
    private val mutableStateFlow = MutableStateFlow(initialState)
    protected var state: State
        get() = mutableStateFlow.value
        set(value) { mutableStateFlow.value = value }
    val stateValue: State
        @Composable
        get() = mutableStateFlow.collectAsState().value

    abstract fun handleAction(action: Action)
}