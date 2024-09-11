package com.lampotrias.gtd

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.lampotrias.gtd.ui.theme.GTDTheme
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.compose.KoinApplication
import org.koin.compose.KoinContext
import org.koin.core.KoinApplication

class MainActivity : ComponentActivity() {
	val detailViewModel: MainViewModel by viewModel()
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		enableEdgeToEdge()
		detailViewModel.toString()
		setContent {
			GTDTheme {
				Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
					Greeting(
						name = "Android",
						modifier = Modifier.padding(innerPadding)
					)
				}
			}
		}
	}
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
//	KoinContext {
		Text(
			text = "Hello $name!",
			modifier = modifier
		)
	}
//}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
	GTDTheme {
		Greeting("Android")
	}
}