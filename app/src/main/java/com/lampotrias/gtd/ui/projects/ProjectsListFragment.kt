package com.lampotrias.gtd.ui.projects

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.lampotrias.gtd.R
import com.lampotrias.gtd.databinding.FragmentProjectListBinding
import com.lampotrias.gtd.domain.model.ProjectDomainModel
import com.lampotrias.gtd.tools.dpToPx
import com.lampotrias.gtd.ui.inbox.InputBoxFragment
import com.lampotrias.gtd.ui.inbox.adapter.DividerItemDecoration
import com.lampotrias.gtd.ui.projects.adapter.ProjectsListAdapter
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ProjectsListFragment : Fragment() {
    private var _binding: FragmentProjectListBinding? = null
    private val binding get() = _binding!!

    private val tasksAdapter =
        ProjectsListAdapter(
            object : ProjectEventListener {
                override fun onProjectClick(task: ProjectDomainModel) {
                    requireActivity()
                        .supportFragmentManager
                        .beginTransaction()
                        .add(
                            R.id.fragment_container_view,
                            InputBoxFragment::class.java,
                            bundleOf("qqq" to "111"),
                        ).addToBackStack(null)
                        .commit()
                }
            },
        )

    private val viewModel: ProjectsListViewModel by viewModel {
        parametersOf(requireArguments().getString("qqq"))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentProjectListBinding.inflate(inflater, container, false)

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

                        tasksAdapter.setTasks(uiState.items)
                    }
                }
            }
        }
    }

    companion object {
        private const val SEPARATOR_HEIGHT = 0.5f
    }
}
