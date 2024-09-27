package com.lampotrias.gtd

import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import org.koin.androidx.fragment.android.setupKoinFragmentFactory
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(R.layout.main_activity) {
    private val detailViewModel: MainViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        setupKoinFragmentFactory()

        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val rootView = findViewById<ViewGroup>(R.id.root_view)

        applyInsets(rootView)
    }


    private fun applyInsets(rootView: ViewGroup) {
        ViewCompat.setOnApplyWindowInsetsListener(rootView) { v, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                leftMargin = insets.left
                topMargin = insets.top
                bottomMargin = insets.bottom
                rightMargin = insets.right
            }

            WindowInsetsCompat.CONSUMED
        }
    }
}