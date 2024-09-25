package com.lampotrias.gtd

import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import com.lampotrias.gtd.ui.inbox.InputBoxFragment
import com.lampotrias.gtd.ui.theme.GTDTheme
import org.koin.androidx.fragment.android.replace
import org.koin.androidx.fragment.android.setupKoinFragmentFactory
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.compose.KoinApplication
import org.koin.compose.KoinContext
import org.koin.core.KoinApplication

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