package com.example.guidomia.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.guidomia.presentation.ui.theme.GuidomiaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GuidomiaTheme {
                val viewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory)
                val state: HomeState by viewModel.state.collectAsState()

                /**
                 * Since we only have Screen, NavHost for navigation is not necessary. We can directly render the HomeScreen
                 */
                HomeScreen(
                    state = state,
                    onEvent = viewModel::onEvent
                )
            }
        }
    }
}