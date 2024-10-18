package com.lampotrias.gtd.domain.usecases

import com.lampotrias.gtd.di.DispatcherProvider
import com.lampotrias.gtd.domain.DataTimeRepository
import com.lampotrias.gtd.ui.datetimeplanner.AvailableDataTimeModel
import com.lampotrias.gtd.ui.datetimeplanner.DataTimeNotificationProvider
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalDateTime

class GetAvailableNotifyDateTimeUseCase(
    private val dateTimeRepository: DataTimeRepository,
    private val dataTimeNotificationProvider: DataTimeNotificationProvider,
    private val dispatcherProvider: DispatcherProvider,
) {
    suspend operator fun invoke(localDateTime: LocalDateTime): AvailableDataTimeModel =
        withContext(dispatcherProvider.io) {
            val timeHolders = dateTimeRepository.getTimeOptions()
            dataTimeNotificationProvider.get(localDateTime, timeHolders)
        }
}
