package com.lampotrias.gtd.ui.datetimeplanner

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.lampotrias.gtd.databinding.FragmentDataTimeNotificationPickerBinding
import com.lampotrias.gtd.tools.DrawableUtils
import com.lampotrias.gtd.tools.OnClickCooldownListener
import com.lampotrias.gtd.tools.dpToPx
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
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

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.uiState.collect { uiState ->
                        binding.finalTime.text = uiState.availableDataTimeModel.toString()
                    }
                }
            }
        }
    }

    private fun initializeUI() {
        binding.finalTime.text = "1231"

        dialog?.window?.decorView?.background =
            DrawableUtils.createCustomBackground(
                Color.WHITE,
                requireContext().dpToPx(DIALOG_CORNER_RADIUS),
            )

        binding.btnOk.setOnClickListener(
            OnClickCooldownListener {
                listener?.onDialogResult(viewModel.localTime.value)
                dismiss()
            },
        )

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
        private const val TITLE_CORNER_RADIUS = 6f
    }
}
