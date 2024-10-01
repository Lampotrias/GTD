package com.lampotrias.gtd.ui.listprojectselector

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.lampotrias.gtd.R
import com.lampotrias.gtd.data.database.tasks.TaskEntity.Companion.LIST_CALENDAR
import com.lampotrias.gtd.data.database.tasks.TaskEntity.Companion.LIST_INBOX
import com.lampotrias.gtd.data.database.tasks.TaskEntity.Companion.LIST_NEXT
import com.lampotrias.gtd.data.database.tasks.TaskEntity.Companion.LIST_SOMEDAY
import com.lampotrias.gtd.data.database.tasks.TaskEntity.Companion.LIST_WAITING
import com.lampotrias.gtd.databinding.FragmentListProjectSelectorBinding
import com.lampotrias.gtd.tools.DrawableUtils
import com.lampotrias.gtd.tools.OnClickCooldownListener
import com.lampotrias.gtd.tools.dpToPx
import com.lampotrias.gtd.ui.listprojectselector.adapter.ListProjectsSelectorAdapter
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ListProjectsSelectorFragment : DialogFragment() {
    private var _binding: FragmentListProjectSelectorBinding? = null
    private val binding get() = _binding!!

    var listener: OnDialogResultListener? = null

    private val listAdapter = ListProjectsSelectorAdapter {
        viewModel.selectList(it.code)
    }

    private val projectAdapter = ListProjectsSelectorAdapter {
        viewModel.selectProject(it.id)
    }

    private val viewModel: ListProjectsSelectorViewModel by viewModel {
        val currentProject = arguments?.getLong(CURRENT_PROJECT_KEY) ?: 0
        val currentList = arguments?.getString(CURRENT_LIST_KEY) ?: ""
        parametersOf(currentProject, currentList)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListProjectSelectorBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding.listSelector) {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        with(binding.projectSelector) {
            adapter = projectAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        dialog?.window?.decorView?.background = DrawableUtils.createCustomBackground(
            Color.WHITE,
            requireContext().dpToPx(DIALOG_CORNER_RADIUS)
        )

        binding.titleStatus.background = DrawableUtils.createCustomBackground(
            Color.LTGRAY,
            requireContext().dpToPx(TITLE_CORNER_RADIUS)
        )

        binding.titleProject.background = DrawableUtils.createCustomBackground(
            Color.LTGRAY,
            requireContext().dpToPx(TITLE_CORNER_RADIUS)
        )

        binding.btnOk.setOnClickListener(OnClickCooldownListener {
            listener?.onDialogResult(
                viewModel.uiState.value.selectedListId,
                viewModel.uiState.value.selectedProjectId
            )
            dismiss()
        })

        binding.btnCancel.setOnClickListener(OnClickCooldownListener {
            dismiss()
        })


        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.uiState.collect { uiState ->
                        listAdapter.setItems(uiState.lists.map {
                            ListProjectAdapterModel(
                                code = it.code,
                                title = it.name,
                                isSelected = it.code == uiState.selectedListId,
                                icon = getListItemDrawable(it.code),
                                iconColor = getListItemIconColor(it.code)
                            )
                        })
                        projectAdapter.setItems(uiState.projects.map {
                            ListProjectAdapterModel(
                                id = it.id,
                                title = it.name,
                                isSelected = it.id == uiState.selectedProjectId,
                                icon = ContextCompat.getDrawable(
                                    requireContext(),
                                    R.drawable.ic_project_dot
                                ),
                                iconColor = Color.BLUE
                            )
                        })
                    }
                }
            }
        }
    }

    private fun getListItemDrawable(listCode: String): Drawable? {
        return when (listCode) {
            LIST_INBOX -> ContextCompat.getDrawable(requireContext(), R.drawable.ic_inbox)
            LIST_NEXT -> ContextCompat.getDrawable(requireContext(), R.drawable.ic_next)
            LIST_WAITING -> ContextCompat.getDrawable(requireContext(), R.drawable.ic_waiting)
            LIST_CALENDAR -> ContextCompat.getDrawable(requireContext(), R.drawable.ic_calendar)
            LIST_SOMEDAY -> ContextCompat.getDrawable(requireContext(), R.drawable.ic_someday)
            else -> null
        }
    }

    @ColorInt
    private fun getListItemIconColor(listCode: String): Int {
        return when (listCode) {
            LIST_INBOX -> Color.BLUE
            LIST_NEXT -> Color.BLUE
            LIST_WAITING -> Color.YELLOW
            LIST_CALENDAR -> Color.GREEN
            LIST_SOMEDAY -> Color.RED
            else -> Color.GRAY
        }
    }

    interface OnDialogResultListener {
        fun onDialogResult(listCode: String, projectId: Long)
    }

    companion object {
        const val CURRENT_PROJECT_KEY = "CURRENT_PROJECT_KEY"
        const val CURRENT_LIST_KEY = "CURRENT_LIST_KEY"

        private const val DIALOG_CORNER_RADIUS = 12f
        private const val TITLE_CORNER_RADIUS = 6f
    }
}