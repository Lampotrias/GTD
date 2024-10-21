package com.lampotrias.gtd.ui.datetimeplanner

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lampotrias.gtd.domain.usecases.GetAvailableNotifyDateTimeUseCase
import com.lampotrias.gtd.tools.SingleEvent
import com.lampotrias.gtd.ui.datetimeplanner.utils.AvailableDataTimeModel
import com.lampotrias.gtd.ui.datetimeplanner.utils.DateOption
import com.lampotrias.gtd.ui.datetimeplanner.utils.TimeOptions
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime

data class DataTimeNotificationPickerScreenUI(
    val isLoading: Boolean = false,
    val availableDataTimeModel: SingleEvent<AvailableDataTimeModel>? = null,
    val openPickerEvent: SingleEvent<LocalDateTime>? = null,
    val selectedDateTime: LocalDateTime,
)

class DataTimeNotificationPickerViewModel(
    @Suppress("unused") private val handle: SavedStateHandle,
    getAvailableNotifyDateTimeUseCase: GetAvailableNotifyDateTimeUseCase,
    currentLocalTime: LocalDateTime,
) : ViewModel() {
    private var wasInitialized = false

    private val _innerScreenUI =
        MutableStateFlow(
            DataTimeNotificationPickerScreenUI(
                selectedDateTime = currentLocalTime,
            ),
        )

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<DataTimeNotificationPickerScreenUI> =
        combine(
            getAvailableNotifyDateTimeUseCase.invoke(currentLocalTime),
            _innerScreenUI,
        ) { availableDataTimeModel, innerScreenUI ->
            DataTimeNotificationPickerScreenUI(
                availableDataTimeModel =
                    if (wasInitialized) {
                        null
                    } else {
                        wasInitialized = true
                        SingleEvent(
                            availableDataTimeModel,
                        )
                    },
                selectedDateTime =
                    if (wasInitialized) {
                        innerScreenUI.selectedDateTime
                    } else {
                        LocalDateTime(
                            availableDataTimeModel.availableDateIntervals.first().date,
                            LocalTime.fromSecondOfDay(
                                availableDataTimeModel.availableTodayTimeIntervals.firstOrNull()?.seconds
                                    ?: availableDataTimeModel.allTimeIntervals.first().seconds,
                            ),
                        )
                    },
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(WHILE_SUBSCRIBED_TIMEOUT),
            initialValue =
                DataTimeNotificationPickerScreenUI(
                    selectedDateTime = currentLocalTime,
                ),
        )

    fun onApplyPickedDateTime(dateTime: LocalDateTime) {
        _innerScreenUI.value =
            _innerScreenUI.value.copy(
                selectedDateTime = dateTime,
            )
    }

    fun onTimeSelected(timeOptions: TimeOptions) {
        _innerScreenUI.value =
            _innerScreenUI.value.copy(
                selectedDateTime =
                    LocalDateTime(
                        uiState.value.selectedDateTime.date,
                        LocalTime.fromSecondOfDay(timeOptions.seconds),
                    ),
            )
    }

    fun onDateSelected(dateInterval: DateOption) {
        _innerScreenUI.value =
            _innerScreenUI.value.copy(
                selectedDateTime =
                    LocalDateTime(
                        dateInterval.date,
                    uiState.value.selectedDateTime.time,
                ),
            )
    }

    companion object {
        private const val WHILE_SUBSCRIBED_TIMEOUT = 5000L
    }
}
