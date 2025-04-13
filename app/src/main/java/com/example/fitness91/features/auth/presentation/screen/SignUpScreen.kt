package com.example.fitness91.features.auth.presentation.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.fitness91.R
import com.example.fitness91.common.domain.state.AppState
import com.example.fitness91.common.presentation.AppLoadingButton
import com.example.fitness91.common.presentation.InputBox
import com.example.fitness91.features.auth.presentation.viewmodel.AuthViewModel

@Composable
fun SignUpScreen(
    onBackClick: () -> Unit,
    moveToSignIn: () -> Unit,
    onSignUpSuccess: () -> Unit,
    authViewModel: AuthViewModel
) {
    var emailText by authViewModel.signUpEmailText
    var firstNameText by authViewModel.signUpFirstNameText
    var passwordText by authViewModel.signUpPasswordText
    var isRemember by authViewModel.signUpIsRemember
    val keyboardController = LocalSoftwareKeyboardController.current
    val firstNameFocusRequester = remember { FocusRequester() }
    val emailFocusRequester = remember { FocusRequester() }
    val passwordFocusRequester = remember { FocusRequester() }
    val authState by authViewModel.authState.collectAsStateWithLifecycle()
    val formError by authViewModel.signUpForm.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        if (firstNameText.isEmpty()) {
            firstNameFocusRequester.requestFocus()
        } else if (emailText.isEmpty()) {
            emailFocusRequester.requestFocus()
        } else {
            passwordFocusRequester.requestFocus()
        }
    }

    LaunchedEffect(authState) {
        if (authState is AppState.Success) {
            onSignUpSuccess()
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            authViewModel.resetState()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        IconButton(
            modifier = Modifier
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.onTertiary),
            onClick = onBackClick
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBackIosNew,
                contentDescription = stringResource(R.string.back_icon)
            )
        }

        Text(
            modifier = Modifier
                .padding(top = 36.dp)
                .align(Alignment.CenterHorizontally),
            text = stringResource(R.string.sign_up_now),
            fontSize = 26.sp,
            fontWeight = FontWeight.SemiBold,
            lineHeight = 34.sp,
            color = MaterialTheme.colorScheme.onSurface
        )

        Text(
            modifier = Modifier
                .padding(top = 12.dp)
                .align(Alignment.CenterHorizontally),
            text = stringResource(R.string.please_fill_the_details_and_create_account),
            fontSize = 16.sp,
            lineHeight = 20.sp,
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colorScheme.tertiary
        )

        InputBox(
            text = firstNameText,
            onChange = { firstNameText = it },
            isError = formError[0],
            modifier = Modifier.padding(top = 24.dp),
            focusRequester = firstNameFocusRequester,
            nextFocusRequester = emailFocusRequester,
            label = R.string.first_name,
            errorText = stringResource(R.string.please_enter_valid_name)
        )

        InputBox(
            text = emailText,
            onChange = { emailText = it },
            isError = formError[1],
            focusRequester = emailFocusRequester,
            nextFocusRequester = passwordFocusRequester,
            label = R.string.email,
            errorText = stringResource(R.string.please_enter_valid_email_address)
        )

        InputBox(
            text = passwordText,
            onChange = { passwordText = it },
            isError = formError[2],
            label = R.string.password,
            focusRequester = passwordFocusRequester,
            errorText = stringResource(R.string.password_must_contain_6_or_more_characters),
            isPassword = true,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                    authViewModel.signUp(
                        firstName = firstNameText,
                        email = emailText,
                        password = passwordText,
                        isRemember = isRemember
                    )
                }
            )
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(top = 12.dp)
                .clip(RoundedCornerShape(4.dp))
                .clickable { isRemember = !isRemember }
                .padding(end = 12.dp)
        ) {
            Checkbox(
                checked = isRemember,
                onCheckedChange = { isRemember = it },
                colors = CheckboxDefaults.colors()
                    .copy(
                        checkedCheckmarkColor = MaterialTheme.colorScheme.surface,
                        checkedBoxColor = MaterialTheme.colorScheme.primary
                    )
            )
            Text(
                text = stringResource(R.string.remember_me),
                fontSize = 18.sp,
                lineHeight = 20.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        AppLoadingButton(
            appState = authState,
            modifier = Modifier.padding(vertical = 28.dp),
            btnText = stringResource(R.string.sign_up)
        ) {
            keyboardController?.hide()
            authViewModel.signUp(
                firstName = firstNameText,
                email = emailText,
                password = passwordText,
                isRemember = isRemember
            )
        }
        Row(
            modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
        ) {
            Text(
                text = stringResource(R.string.already_have_an_account),
                color = MaterialTheme.colorScheme.tertiary,
                fontSize = 14.sp,
                lineHeight = 16.sp,
                fontWeight = FontWeight.Normal
            )
            Text(
                text = stringResource(R.string.sign_in),
                color = MaterialTheme.colorScheme.secondary,
                fontSize = 14.sp,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        moveToSignIn()
                    },
                lineHeight = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }

        AnimatedVisibility(
            visible = authState is AppState.Error,
            enter = expandVertically(),
            exit = shrinkVertically(),
            modifier = Modifier.padding(12.dp)

        ) {
            when (authState) {
                is AppState.Error -> {
                    Text(
                        text = (authState as AppState.Error).error,
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 16.sp,
                        lineHeight = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                else -> {}
            }
        }
    }
}