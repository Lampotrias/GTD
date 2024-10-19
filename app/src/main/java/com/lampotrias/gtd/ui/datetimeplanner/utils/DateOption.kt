package com.lampotrias.gtd.ui.datetimeplanner.utils

import kotlinx.datetime.LocalDate

sealed class DateOption(
    val date: LocalDate,
) {
    class Today(
        date: LocalDate,
    ) : DateOption(date)

    class Tomorrow(
        date: LocalDate,
    ) : DateOption(date)

    class OnWeekend(
        date: LocalDate,
    ) : DateOption(date)

    class Sunday(
        date: LocalDate,
    ) : DateOption(date)

    class NextWeek(
        date: LocalDate,
    ) : DateOption(date)

    class InTwoWeeks(
        date: LocalDate,
    ) : DateOption(date)
}
