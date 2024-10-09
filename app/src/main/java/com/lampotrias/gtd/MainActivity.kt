package com.lampotrias.gtd

import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import com.lampotrias.gtd.lifecycle.ActivityLifecycleDelegate
import org.koin.android.ext.android.inject
import org.koin.androidx.fragment.android.setupKoinFragmentFactory

class MainActivity : AppCompatActivity(R.layout.main_activity) {
    private val activityLifecycleDelegate by inject<ActivityLifecycleDelegate>()

    override fun onCreate(savedInstanceState: Bundle?) {
        setupKoinFragmentFactory()

        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val rootView = findViewById<ViewGroup>(R.id.root_view)
        lifecycle.addObserver(activityLifecycleDelegate)
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
