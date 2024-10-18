package com.lampotrias.gtd.ui.addtask

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.RippleDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.lampotrias.gtd.databinding.FragmentTaskAddUpdateBinding
import com.lampotrias.gtd.domain.model.TagDomainModel
import com.lampotrias.gtd.tools.DrawableUtils
import com.lampotrias.gtd.tools.OnClickCooldownListener
import com.lampotrias.gtd.tools.changeVisibility
import com.lampotrias.gtd.tools.dpToPx
import com.lampotrias.gtd.ui.listprojectselector.ListProjectsSelectorFragment
import com.lampotrias.gtd.ui.listprojectselector.ListProjectsSelectorFragment.Companion.CURRENT_LIST_KEY
import com.lampotrias.gtd.ui.listprojectselector.ListProjectsSelectorFragment.Companion.CURRENT_PROJECT_KEY
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.util.Calendar

class TaskAddUpdateFragment : Fragment() {
    private var _binding: FragmentTaskAddUpdateBinding? = null
    private val binding get() = _binding!!

    private var editTaskId = 0L
    private val isUpdateMode: Boolean
        get() = editTaskId != 0L

    private val viewModel: TaskAddUpdateViewModel by viewModel {
        editTaskId = requireArguments().getLong(TASK_ID_PARAM, 0L)
        parametersOf(editTaskId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentTaskAddUpdateBinding.inflate(inflater, container, false)

        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.uiState.collect { uiState ->
                        binding.progress.changeVisibility(uiState.isLoading)

                        if (uiState.data != null) {
                            Toast
                                .makeText(requireContext(), uiState.data, Toast.LENGTH_SHORT)
                                .show()
                            binding.root.postDelayed({
                                requireActivity().supportFragmentManager.popBackStack()
                            }, CLOSE_FRAGMENT_DELAY)
                        }

                        uiState.currentTask?.getContentIfNotHandled()?.let {
                            binding.editTextTaskName.setText(it.name)
                            binding.editTextTaskDescription.setText(it.description)
                            binding.projectName.text = it.project?.name
                            binding.listName.text = it.list
                            binding.completedCheckbox.isChecked = it.isCompleted
                        } ?: run {
                            binding.listName.text = uiState.selectedList?.name
                            binding.projectName.text = uiState.selectedProject?.name
                        }

                        binding.projectName.changeVisibility(
                            uiState.selectedProject?.name != null &&
                                uiState.selectedProject.name.isNotBlank(),
                        )

                        binding.tagsList.text =
                            "Теги: ${
                                uiState.selectedCustomTags?.joinToString(", ") { it.name }
                            }"

                        binding.tagsTime.text = "Время: ${uiState.selectedTimeTag?.name}"
                        binding.tagsEnergy.text = "Энергия: ${uiState.selectedEnergyTags?.name}"
                        binding.tagsPriority.text =
                            "Приоритет: ${uiState.selectedPriorityTags?.name}"

                        binding.listContainer.setOnClickListener(
                            OnClickCooldownListener {
                                val dialog =
                                    ListProjectsSelectorFragment().apply {
                                        arguments =
                                            Bundle().apply {
                                                putLong(
                                                    CURRENT_PROJECT_KEY,
                                                    uiState.selectedProject?.id ?: 0L,
                                                )
                                                putString(
                                                    CURRENT_LIST_KEY,
                                                    uiState.selectedList?.code ?: "",
                                                )
                                            }
                                    }

                                dialog.listener =
                                    object : ListProjectsSelectorFragment.OnDialogResultListener {
                                        override fun onDialogResult(
                                            listCode: String,
                                            projectId: Long,
                                        ) {
                                            viewModel.applyListProject(listCode, projectId)
                                        }
                                    }

                                dialog.show(parentFragmentManager, "CustomDialog")
                            },
                        )

                        uiState.tagsDialog?.getContentIfNotHandled()?.let {
                            showSelectTagsDialog(it)
                        }

                        uiState.priorityDialog?.getContentIfNotHandled()?.let {
                            showSelectPriorityDialog(it)
                        }

                        uiState.timeDialog?.getContentIfNotHandled()?.let {
                            showSelectTimeDialog(it)
                        }

                        uiState.energyDialog?.getContentIfNotHandled()?.let {
                            showSelectEnergyDialog(it)
                        }
                    }
                }
            }
        }

        initializeUI()
    }

    private fun initializeUI() {
        binding.btnTag.background = createButtonBackground()
        binding.btnTime.background = createButtonBackground()
        binding.btnEnergy.background = createButtonBackground()
        binding.btnPriority.background = createButtonBackground()
        binding.btnDue.background = createButtonBackground()
        binding.btnNotification.background = createButtonBackground()
        binding.btnCycle.background = createButtonBackground()

        binding.btnSave.setOnClickListener(
            OnClickCooldownListener {
                val name = binding.editTextTaskName.text.toString()
                val description = binding.editTextTaskDescription.text.toString()
                val isCompleted = binding.completedCheckbox.isChecked
                if (isUpdateMode) {
                    viewModel.updateTask(
                        editTaskId,
                        name,
                        description,
                        isCompleted,
                    )
                } else {
                    viewModel.saveTask(name, description, isCompleted)
                }
            },
        )

        binding.btnNotification.setOnClickListener(
            OnClickCooldownListener {
                viewModel.clickOpenNotificationDialog()
            },
        )

        binding.btnCancel.setOnClickListener(
            OnClickCooldownListener {
                requireActivity().supportFragmentManager.popBackStack()
            },
        )

        binding.btnDue.setOnClickListener(
            OnClickCooldownListener {
                showDatePicker { day, month, year ->
                }
            },
        )

        binding.btnEnergy.setOnClickListener(
            OnClickCooldownListener {
                viewModel.clickOpenDialogEnergy()
            },
        )

        binding.btnPriority.setOnClickListener(
            OnClickCooldownListener {
                viewModel.clickOpenDialogPriority()
            },
        )

        binding.btnTime.setOnClickListener(
            OnClickCooldownListener {
                viewModel.clickOpenDialogTime()
            },
        )

        binding.btnTag.setOnClickListener(
            OnClickCooldownListener {
                viewModel.clickOpenDialogTags()
            },
        )
    }

    private fun createButtonBackground(): Drawable =
        RippleDrawable(
            ColorStateList.valueOf(Color.parseColor("#14000000")),
            DrawableUtils.createCustomBackground(
                Color.LTGRAY,
                requireContext().dpToPx(BUTTON_CORNER_RADIUS),
            ),
            null,
        )

    private fun showSelectTagsDialog(tags: List<TagDomainModel>) {
        showMultiSelectDialog(
            title = "Теги",
            items = tags.map { it.name },
            onItemSelected = { selectedTags ->
                viewModel.selectCustomTags(
                    tags.filterIndexed { index, _ ->
                        selectedTags.contains(index)
                    },
                )
            },
        )
    }

    private fun showSelectTimeDialog(tags: List<TagDomainModel>) {
        showSelectDialog(
            title = "Время на задачу",
            items = tags.map { it.name },
            onItemSelected = { selectedPosition ->
                viewModel.selectTimeTags(tags[selectedPosition])
            },
        )
    }

    private fun showSelectEnergyDialog(tags: List<TagDomainModel>) {
        showSelectDialog(
            title = "Теги",
            items = tags.map { it.name },
            onItemSelected = { selectedPosition ->
                viewModel.selectEnergyTags(tags[selectedPosition])
            },
        )
    }

    private fun showSelectPriorityDialog(tags: List<TagDomainModel>) {
        showSelectDialog(
            title = "Теги",
            items = tags.map { it.name },
            onItemSelected = { selectedPosition ->
                viewModel.selectPriorityTags(tags[selectedPosition])
            },
        )
    }

    private fun showSelectDialog(
        title: String,
        items: List<String>,
        onItemSelected: (Int) -> Unit,
    ) {
        val itemsArray = items.toTypedArray()

        AlertDialog
            .Builder(requireContext())
            .setTitle(title)
            .setItems(itemsArray) { dialog, which ->
                dialog.dismiss()
                onItemSelected(which)
            }.setNegativeButton("Отмена") { dialog, _ ->
                dialog.dismiss()
            }.show()
    }

    private fun showMultiSelectDialog(
        @Suppress("SameParameterValue") title: String,
        items: List<String>,
        onItemSelected: (Map<Int, String>) -> Unit,
    ) {
        val selectedTags = mutableMapOf<Int, String>()

        AlertDialog
            .Builder(requireContext())
            .setTitle(title)
            .setMultiChoiceItems(items.toTypedArray(), null) { _, which, isChecked ->
                val tag = items[which]
                if (isChecked) {
                    selectedTags[which] = tag
                } else {
                    selectedTags.remove(which)
                }
            }.setPositiveButton("ОК") { _, _ ->
                onItemSelected.invoke(selectedTags)
            }.setNegativeButton("Отмена", null)
            .create()
            .show()
    }

    private fun showDatePicker(onDateSelected: (day: Int, month: Int, year: Int) -> Unit) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog =
            DatePickerDialog(
                requireContext(),
                { _, selectedYear, selectedMonth, selectedDay ->
                    onDateSelected(selectedDay, selectedMonth, selectedYear)
                },
                year,
                month,
                day,
            )

        datePickerDialog.show()
    }

    companion object {
        const val TASK_ID_PARAM = "task_id"
        private const val BUTTON_CORNER_RADIUS = 8f
        private const val CLOSE_FRAGMENT_DELAY = 2000L
    }
}
