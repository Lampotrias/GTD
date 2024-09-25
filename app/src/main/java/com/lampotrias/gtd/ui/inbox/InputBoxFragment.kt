package com.lampotrias.gtd.ui.inbox

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lampotrias.gtd.R
import com.lampotrias.gtd.data.database.tasks.TaskDao
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class InputBoxFragment(private val taskDao: TaskDao) : Fragment() {

    private val viewModel: InputBoxViewModel by viewModel {
        parametersOf(requireArguments().getString("qqq"))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.toString()
        return inflater.inflate(R.layout.fragment_input_box, container, false)
    }
}