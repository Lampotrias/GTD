package com.lampotrias.gtd.ui.tasksproject

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.lampotrias.gtd.R
import com.lampotrias.gtd.databinding.FragmentInputBoxBinding
import com.lampotrias.gtd.domain.model.TaskDomainModel
import com.lampotrias.gtd.tools.dpToPx
import com.lampotrias.gtd.ui.DividerItemDecoration
import com.lampotrias.gtd.ui.TaskEventListener
import com.lampotrias.gtd.ui.addtask.TaskAddUpdateFragment
import com.lampotrias.gtd.ui.addtask.TaskAddUpdateFragment.Companion.TASK_ID_PARAM
import com.lampotrias.gtd.ui.tasksproject.adapter.TaskAdapter
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class TasksProjectFragment : Fragment() {
    private var _binding: FragmentInputBoxBinding? = null
    private val binding get() = _binding!!

    private val tasksAdapter =
        TaskAdapter(
            object : TaskEventListener {
                override fun onTaskClick(task: TaskDomainModel) {
                    val bundle =
                        Bundle().apply {
                            putLong(TASK_ID_PARAM, task.id)
                        }
                    requireActivity()
                        .supportFragmentManager
                        .beginTransaction()
                        .add(
                            R.id.fragment_container_view,
                            TaskAddUpdateFragment::class.java,
                            bundle,
                        ).addToBackStack(null)
                        .commit()
                }
//
                override fun onTaskCompleteChange(task: TaskDomainModel) {
                    viewModel.taskCompleteChange(task)
}
//
//                override fun onTaskFavoriteClick(task: TaskDomainModel) {
//                    viewModel.taskFavoriteClick(task)
//                }
            },
        )

    private val viewModel: TasksProjectViewModel by viewModel {
        parametersOf(requireArguments().getLong(PROJECT_KEY))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentInputBoxBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        with(binding.recyclerView) {
            adapter = tasksAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(
                DividerItemDecoration(
                    thickness = requireContext().dpToPx(SEPARATOR_HEIGHT),
                    color = Color.GRAY,
                ),
            )
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.uiState.collect { uiState ->
                        Log.e("asdasd", uiState.toString())
                        binding.progress.visibility =
                            if (uiState.isLoading) View.VISIBLE else View.GONE

                        tasksAdapter.setTasks(uiState.project?.tasks ?: emptyList())
                    }
                }
            }
        }
    }

    companion object {
        private const val SEPARATOR_HEIGHT = 0.5f
        const val PROJECT_KEY = "PROJECT_KEY"
    }
}
