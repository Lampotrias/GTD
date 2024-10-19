package com.lampotrias.gtd.ui.datetimeplanner.utils

sealed class TimeOptions(
    val seconds: Int,
) {
    class Morning(
        seconds: Int,
    ) : TimeOptions(seconds)

    class Afternoon(
        seconds: Int,
    ) : TimeOptions(seconds)

    class Evening(
        seconds: Int,
    ) : TimeOptions(seconds)

    class Night(
        seconds: Int,
    ) : TimeOptions(seconds)
}
