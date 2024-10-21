package com.lampotrias.gtd.ui.datetimeplanner

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.setPadding
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.lampotrias.gtd.databinding.FragmentDataTimeNotificationPickerBinding
import com.lampotrias.gtd.tools.DrawableUtils
import com.lampotrias.gtd.tools.OnClickCooldownListener
import com.lampotrias.gtd.tools.dpToPx
import com.lampotrias.gtd.ui.datetimeplanner.utils.DateOption
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.char
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DataTimeNotificationPickerFragment : DialogFragment() {
    private var _binding: FragmentDataTimeNotificationPickerBinding? = null
    private val binding get() = _binding!!

    var listener: OnDialogResultListener? = null

    private val viewModel: DataTimeNotificationPickerViewModel by viewModel {
        parametersOf(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentDataTimeNotificationPickerBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        initializeUI()

        val localTimeFormat =
            LocalTime.Format {
                hour()
                char(':')
                minute()
            }

        val localDateTimeFormat =
            LocalDateTime.Format {
                dayOfMonth()
                char('.')
                monthNumber()
                char('.')
                year()
                char(' ')
                hour()
                char(':')
                minute()
            }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.uiState.collect { uiState ->

                        binding.btnOk.setOnClickListener(
                            OnClickCooldownListener {
                                listener?.onDialogResult(viewModel.uiState.value.selectedDateTime)
                                dismiss()
                            },
                        )

                        binding.formattedDateTime.text =
                            uiState.selectedDateTime.format(localDateTimeFormat)

                        binding.formattedDateTime.setOnClickListener(
                            OnClickCooldownListener {
                                val datePicker =
                                    MaterialDatePicker.Builder
                                        .datePicker()
                                        .setSelection(
                                            uiState.selectedDateTime
                                                .toInstant(TimeZone.currentSystemDefault())
                                                .toEpochMilliseconds(),
                                        ).setTitleText("Select date")
                                        .build()

                                datePicker.addOnPositiveButtonClickListener {
                                    val newDate =
                                        Instant.fromEpochMilliseconds(it).toLocalDateTime(
                                            TimeZone.currentSystemDefault(),
                                        )

                                    val timePicker =
                                        MaterialTimePicker
                                            .Builder()
                                            .apply {
                                                setHour(uiState.selectedDateTime.hour)
                                                setMinute(uiState.selectedDateTime.minute)
                                            }.build()

                                    timePicker.addOnPositiveButtonClickListener {
                                        val newTime = LocalTime(timePicker.hour, timePicker.minute)
                                        viewModel.onApplyPickedDateTime(
                                            LocalDateTime(
                                                newDate.date,
                                                newTime,
                                            ),
                                        )
                                    }
                                    timePicker.show(childFragmentManager, "TIME_PICKER")
                                }
                                datePicker.show(
                                    childFragmentManager,
                                    "DATE_PICKER",
                                )
                            },
                        )

                        uiState.availableDataTimeModel?.getContentIfNotHandled()?.let { result ->
                            binding.dateContainer.removeAllViews()
                            binding.timeContainer.removeAllViews()
                            result.allTimeIntervals
                                .forEach { timeInterval ->
                                    TextView(requireContext())
                                        .apply {
                                            text =
                                                LocalTime
                                                    .fromSecondOfDay(timeInterval.seconds)
                                                    .format(localTimeFormat)
                                            layoutParams =
                                                LinearLayout
                                                    .LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
                                                    .apply {
                                                        gravity = Gravity.CENTER_HORIZONTAL
                                                    }
                                            setPadding(requireContext().dpToPx(3))
                                            setOnClickListener(
                                                OnClickCooldownListener {
                                                    viewModel.onTimeSelected(timeInterval)
                                                },
                                            )
                                        }.also {
                                            binding.timeContainer.addView(it)
                                        }
                                }

                            result.availableDateIntervals.forEach { dateInterval ->
                                TextView(requireContext())
                                    .apply {
                                        text =
                                            when (dateInterval) {
                                                is DateOption.InTwoWeeks -> "Через неделю"
                                                is DateOption.NextWeek -> "На следующей неделе"
                                                is DateOption.OnWeekend -> "На выходных"
                                                is DateOption.Sunday -> "В воскресенье"
                                                is DateOption.Today -> "Сегодня"
                                                is DateOption.Tomorrow -> "Завтра"
                                            }
                                        layoutParams =
                                            LinearLayout
                                                .LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
                                                .apply {
                                                    gravity = Gravity.CENTER_HORIZONTAL
                                                }
                                        setPadding(requireContext().dpToPx(3))
                                        setOnClickListener(
                                            OnClickCooldownListener {
                                                viewModel.onDateSelected(dateInterval)
                                            },
                                        )
                                    }.also {
                                        binding.dateContainer.addView(it)
                                    }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun initializeUI() {
        dialog?.window?.decorView?.background =
            DrawableUtils.createCustomBackground(
                Color.WHITE,
                requireContext().dpToPx(DIALOG_CORNER_RADIUS),
            )

        binding.btnOk.background = DrawableUtils.createCustomBackground(Color.GREEN, 30f)

        binding.btnCancel.background = DrawableUtils.createCustomBackground(Color.LTGRAY, 30f)
        binding.btnCancel.setOnClickListener(
            OnClickCooldownListener {
                dismiss()
            },
        )
    }

    interface OnDialogResultListener {
        fun onDialogResult(notifyTime: LocalDateTime)
    }

    companion object {
        private const val DIALOG_CORNER_RADIUS = 12f
    }
}
