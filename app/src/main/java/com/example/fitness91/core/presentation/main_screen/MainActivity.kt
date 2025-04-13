package com.example.fitness91.core.presentation.main_screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.fitness91.core.data.Preferences
import com.example.fitness91.core.domain.utils.ThemeOption
import com.example.fitness91.core.presentation.navigation.RootNavGraph
import com.example.fitness91.core.presentation.utils.Utils.getStartDestination
import com.example.fitness91.theme.Fitness91Theme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val isReady = MutableStateFlow<Boolean?>(null)
        val themeState = MutableStateFlow(ThemeOption.SYSTEM)


        lifecycleScope.launch {
            coroutineScope {
                launch(Dispatchers.Default) {
                    isReady.value = preferences.getIsOnboarded()
                }
                launch(Dispatchers.IO) {
                    preferences.getTheme().collect {
                        themeState.value = it
                    }
                }
            }
        }

        val splashScreen = installSplashScreen()

        splashScreen.setKeepOnScreenCondition { isReady.value == null }

        setContent {
            val readyState by isReady.collectAsStateWithLifecycle()
            val theme by themeState.collectAsStateWithLifecycle()
            readyState?.let { ready ->
                val rootNavController = rememberNavController()

                Fitness91Theme(themeOption = theme) {
                    RootNavGraph(
                        rootNavController = rootNavController,
                        startDestination = getStartDestination(ready),
                        onBackOrFinish = { handleBackClick(rootNavController) }
                    )
                }
            }
        }
    }

    private fun handleBackClick(rootNavController: NavHostController) {
        if (rootNavController.previousBackStackEntry == null) finish()
        else rootNavController.navigateUp()
    }
}
