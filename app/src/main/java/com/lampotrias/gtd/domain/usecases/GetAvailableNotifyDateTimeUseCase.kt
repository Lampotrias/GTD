package com.lampotrias.gtd.domain.usecases

import com.lampotrias.gtd.di.DispatcherProvider
import com.lampotrias.gtd.domain.DataTimeRepository
import com.lampotrias.gtd.ui.datetimeplanner.DataTimeNotificationProvider
import com.lampotrias.gtd.ui.datetimeplanner.utils.AvailableDataTimeModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flowOf
import kotlinx.datetime.LocalDateTime

class GetAvailableNotifyDateTimeUseCase(
    private val dateTimeRepository: DataTimeRepository,
    private val dataTimeNotificationProvider: DataTimeNotificationProvider,
    private val dispatcherProvider: DispatcherProvider,
) {
    operator fun invoke(localDateTime: LocalDateTime): Flow<AvailableDataTimeModel> =
        dateTimeRepository.getTimeOptions().flatMapMerge { timeHolders ->
            flowOf(dataTimeNotificationProvider.get(localDateTime, timeHolders))
        }
}
