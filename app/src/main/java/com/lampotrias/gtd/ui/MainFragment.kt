package com.lampotrias.gtd.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.lampotrias.gtd.R
import com.lampotrias.gtd.databinding.FragmentMainBinding
import com.lampotrias.gtd.tools.OnClickCooldownListener
import com.lampotrias.gtd.ui.addtask.TaskAddUpdateFragment
import com.lampotrias.gtd.ui.inbox.InputBoxFragment

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnInputBox.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .add(
                    R.id.fragment_container_view,
                    InputBoxFragment::class.java,
                    bundleOf("qqq" to "111")
                )
                .addToBackStack(null)
                .commit()
        }

        binding.btnAddTask.setOnClickListener(OnClickCooldownListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .add(
                    R.id.fragment_container_view,
                    TaskAddUpdateFragment::class.java,
                    bundleOf("qqq" to "111")
                )
                .addToBackStack(null)
                .commit()
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}