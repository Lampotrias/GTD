package com.lampotrias.gtd.ui.addtask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.lampotrias.gtd.databinding.FragmentTaskAddUpdateBinding
import com.lampotrias.gtd.tools.OnClickCooldownListener
import com.lampotrias.gtd.tools.changeVisibility
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class TaskAddUpdateFragment : Fragment() {
    private var _binding: FragmentTaskAddUpdateBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TaskAddUpdateViewModel by viewModel()

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
                            Toast.makeText(requireContext(), uiState.data, Toast.LENGTH_SHORT).show()
                            binding.root.postDelayed({
                                requireActivity().supportFragmentManager.popBackStack()
                            }, 2000)
                        }
                    }
                }
            }
        }

        binding.btnSave.setOnClickListener(
            OnClickCooldownListener {
                val name = binding.editTextTaskName.text.toString()
                val description = binding.editTextTaskDescription.text.toString()
                viewModel.saveTask(name, description)
            }
        )

        binding.btnCancel.setOnClickListener(
            OnClickCooldownListener {
                requireActivity().supportFragmentManager.popBackStack()
            }
        )
    }
}
