package com.example.fitness91.features.settings.presentation.screen

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Commit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.fitness91.BuildConfig
import com.example.fitness91.R
import com.example.fitness91.common.domain.state.AppState
import com.example.fitness91.common.presentation.HeaderBackIcon
import com.example.fitness91.features.auth.presentation.viewmodel.AuthViewModel
import com.example.fitness91.features.settings.presentation.components.ProfileReDirectItem
import com.example.fitness91.features.settings.presentation.components.ThemeSelector
import com.example.fitness91.features.settings.presentation.utils.ProfileReDirectItems
import com.example.fitness91.features.settings.presentation.viewmodel.SettingsViewModel

@Composable
fun SettingScreen(
    onSignOut: () -> Unit,
    onBackClick: () -> Unit,
    authViewModel: AuthViewModel = hiltViewModel(),
    settingsViewModel: SettingsViewModel
) {
    val profileReDirectItems = getProfileReDirectionItems()
    val authState by authViewModel.authState.collectAsStateWithLifecycle()
    var showSignOutDialog by remember { mutableStateOf(false) }

    LaunchedEffect(authState) {
        if (authState is AppState.Success) {
            showSignOutDialog = false
            onSignOut()
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            authViewModel.resetState()
        }
    }

    if (showSignOutDialog) {
        AlertDialog(onDismissRequest = { showSignOutDialog = false }, title = {
            Text(
                text = stringResource(R.string.sign_out),
                style = MaterialTheme.typography.titleLarge
            )
        }, text = {
            Text(
                text = stringResource(R.string.are_you_sure_you_want_to_sign_out),
                style = MaterialTheme.typography.bodyMedium
            )
        }, confirmButton = {
            TextButton(onClick = { authViewModel.signOut() }) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(text = stringResource(R.string.yes))
                    AnimatedVisibility(
                        visible = authState is AppState.Loading,
                        enter = expandHorizontally(),
                        exit = shrinkHorizontally()
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(16.dp),
                            strokeWidth = 3.dp
                        )
                    }
                }
            }
        }, dismissButton = {
            TextButton(onClick = { showSignOutDialog = false }) {
                Text(text = stringResource(R.string.no))
            }
        })
    }

    val context = LocalContext.current

    val themeState by settingsViewModel.themeState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(12.dp)
    ) {

        HeaderBackIcon(headerText = stringResource(R.string.settings), onBackClick = onBackClick)

        ThemeSelector(
            currentTheme = themeState,
            onThemeSelect = { settingsViewModel.onThemeChange(selectedTheme = it) }
        )
        profileReDirectItems.forEachIndexed { index, profileReDirectItem ->
            ProfileReDirectItem(
                profileReDirectItem = profileReDirectItem,
                onItemClick = {
                    when (index) {
                        0 -> showAppVersion(context = context)
                        1 -> showSignOutDialog = true
                    }
                }
            )
        }
    }
}

@Composable
fun getProfileReDirectionItems(): List<ProfileReDirectItems> {
    return listOf(
        ProfileReDirectItems(
            Icons.Default.Commit,
            stringResource(R.string.version),
            stringResource(R.string.version_icon)
        ),
        ProfileReDirectItems(
            Icons.AutoMirrored.Filled.Logout,
            stringResource(R.string.sign_out),
            stringResource(R.string.sign_out_icon)
        )
    )
}

fun showAppVersion(context: Context) {
    Toast.makeText(
        context,
        context.getString(R.string.app_version, BuildConfig.VERSION_NAME),
        Toast.LENGTH_SHORT
    ).show()
}
