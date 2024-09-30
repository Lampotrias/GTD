package com.lampotrias.gtd.ui.addtask

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
import com.lampotrias.gtd.domain.model.ListDomainModel
import com.lampotrias.gtd.domain.model.TagDomainModel
import com.lampotrias.gtd.tools.DrawableUtils
import com.lampotrias.gtd.tools.OnClickCooldownListener
import com.lampotrias.gtd.tools.changeVisibility
import com.lampotrias.gtd.tools.dpToPx
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Calendar

class TaskAddUpdateFragment : Fragment() {
    private var _binding: FragmentTaskAddUpdateBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TaskAddUpdateViewModel by viewModel()

    private val selectedCustomTags = mutableListOf<TagDomainModel>()
    private var currentList: ListDomainModel? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskAddUpdateBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.uiState.collect { uiState ->
                        binding.progress.changeVisibility(uiState.isLoading)

                        if (uiState.data != null) {
                            Toast.makeText(requireContext(), uiState.data, Toast.LENGTH_SHORT)
                                .show()
                            binding.root.postDelayed({
                                requireActivity().supportFragmentManager.popBackStack()
                            }, CLOSE_FRAGMENT_DELAY)
                        }

                        if (currentList == null && uiState.lists.isNotEmpty()) {
                            currentList = uiState.lists.first()
                            binding.listName.text = uiState.lists.first().name
                        }

                        binding.listContainer.setOnClickListener(
                            OnClickCooldownListener {
                            }
                        )

                        uiState.tagsDialog?.getContentIfNotHandled()?.let {
                            showSelectTagsDialog(it)
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
        binding.btnDue.background = createButtonBackground()
        binding.btnNotification.background = createButtonBackground()
        binding.btnCycle.background = createButtonBackground()

        binding.btnSave.setOnClickListener(
            OnClickCooldownListener {
                val name = binding.editTextTaskName.text.toString()
                val description = binding.editTextTaskDescription.text.toString()
                viewModel.saveTask(name, description, selectedCustomTags)
            }
        )

        binding.btnCancel.setOnClickListener(
            OnClickCooldownListener {
                requireActivity().supportFragmentManager.popBackStack()
            }
        )

        binding.btnDue.setOnClickListener(
            OnClickCooldownListener {
                showDatePicker { day, month, year ->
                }
            }
        )

        binding.btnEnergy.setOnClickListener(
            OnClickCooldownListener {
                showSelectDialog(
                    title = "Нужно энергии",
                    items = listOf("мало энергии", "среднее количество", "много энергии"),
                    onItemSelected = { position, value ->
                    }
                )
            }
        )

        binding.btnTime.setOnClickListener(
            OnClickCooldownListener {
                showSelectDialog(
                    title = "Время на задачу",
                    items = listOf("5 минут", "10 минут", "15 минут", "30 минут"),
                    onItemSelected = { position, value ->
                    }
                )
            }
        )

        binding.btnTag.setOnClickListener(
            OnClickCooldownListener {
                viewModel.clickOpenDialogTags()
            }
        )
    }

    private fun createButtonBackground(): Drawable {
        return RippleDrawable(
            ColorStateList.valueOf(Color.parseColor("#14000000")),
            DrawableUtils.createCustomBackground(
                Color.LTGRAY,
                requireContext().dpToPx(BUTTON_CORNER_RADIUS)
            ),
            null
        )
    }

    private fun showSelectTagsDialog(tags: List<TagDomainModel>) {
        showMultiSelectDialog(
            title = "Теги",
            items = tags.map { it.name },
            onItemSelected = { selectedTags ->
                selectedCustomTags.clear()
                selectedTags.forEach { (index, _) ->
                    selectedCustomTags.add(tags[index])
                }
            }
        )
    }

    private fun showSelectDialog(
        title: String,
        items: List<String>,
        onItemSelected: (Int, String) -> Unit
    ) {
        val itemsArray = items.toTypedArray()

        AlertDialog.Builder(requireContext())
            .setTitle(title)
            .setItems(itemsArray) { dialog, which ->
                val selectedItem = items[which]
                dialog.dismiss()
                onItemSelected(which, selectedItem)
            }
            .setNegativeButton("Отмена") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun showMultiSelectDialog(
        @Suppress("SameParameterValue") title: String,
        items: List<String>,
        onItemSelected: (Map<Int, String>) -> Unit
    ) {
        val selectedTags = mutableMapOf<Int, String>()

        AlertDialog.Builder(requireContext())
            .setTitle(title)
            .setMultiChoiceItems(items.toTypedArray(), null) { _, which, isChecked ->
                val tag = items[which]
                if (isChecked) {
                    selectedTags[which] = tag
                } else {
                    selectedTags.remove(which)
                }
            }
            .setPositiveButton("ОК") { _, _ ->
                onItemSelected.invoke(selectedTags)
            }
            .setNegativeButton("Отмена", null)
            .create()
            .show()
    }

    private fun showDatePicker(onDateSelected: (day: Int, month: Int, year: Int) -> Unit) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDay ->
                onDateSelected(selectedDay, selectedMonth, selectedYear)
            },
            year,
            month,
            day
        )

        datePickerDialog.show()
    }

    companion object {
        private const val BUTTON_CORNER_RADIUS = 8f
        private const val CLOSE_FRAGMENT_DELAY = 2000L
    }
}
