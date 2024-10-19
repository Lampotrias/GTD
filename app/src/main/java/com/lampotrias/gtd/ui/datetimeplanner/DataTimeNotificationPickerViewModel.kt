package com.lampotrias.gtd.ui.datetimeplanner

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lampotrias.gtd.domain.usecases.GetAvailableNotifyDateTimeUseCase
import com.lampotrias.gtd.ui.datetimeplanner.utils.AvailableDataTimeModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.stateIn
import kotlinx.datetime.LocalDateTime

data class DataTimeNotificationPickerScreenUI(
    val isLoading: Boolean = false,
    val availableDataTimeModel: AvailableDataTimeModel = AvailableDataTimeModel(),
)

class DataTimeNotificationPickerViewModel(
    @Suppress("unused") private val handle: SavedStateHandle,
    getAvailableNotifyDateTimeUseCase: GetAvailableNotifyDateTimeUseCase,
    currentLocalTime: LocalDateTime,
) : ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val localTime: MutableStateFlow<LocalDateTime> = MutableStateFlow(currentLocalTime)

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<DataTimeNotificationPickerScreenUI> =
        localTime
            .flatMapConcat { localTime ->
                getAvailableNotifyDateTimeUseCase.invoke(localTime)
            }.combine(_isLoading) { availableDataTimeModel, isLoading ->
                DataTimeNotificationPickerScreenUI(
                    isLoading = isLoading,
                    availableDataTimeModel = availableDataTimeModel,
                )
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(WHILE_SUBSCRIBED_TIMEOUT),
                initialValue = DataTimeNotificationPickerScreenUI(),
            )

    fun updateTime(dateTime: LocalDateTime) {
        localTime.value = dateTime
    }

    companion object {
        private const val WHILE_SUBSCRIBED_TIMEOUT = 5000L
    }
}
