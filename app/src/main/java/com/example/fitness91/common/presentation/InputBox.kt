package com.example.fitness91.common.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fitness91.R

@Composable
fun InputBox(
    text: String,
    onChange: (String) -> Unit,
    isError: Boolean,
    modifier: Modifier = Modifier,
    focusRequester: FocusRequester,
    nextFocusRequester: FocusRequester? = null,
    label: Int,
    errorText: String,
    isPassword: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
    keyboardActions: KeyboardActions = KeyboardActions(
        onNext = { nextFocusRequester?.requestFocus() }
    ),
) {
    var isTextVisible by remember { mutableStateOf(true) }

    OutlinedTextField(
        value = text,
        onValueChange = { onChange(it) },
        isError = isError,
        label = { Text(text = stringResource(label)) },
        singleLine = true,
        modifier = modifier
            .padding(top = 16.dp)
            .fillMaxWidth()
            .focusRequester(focusRequester),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        visualTransformation =
        if (isTextVisible && isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        trailingIcon = {
            if (isPassword) {
                val icon = if (isTextVisible) Icons.Filled.VisibilityOff
                else Icons.Filled.Visibility

                IconButton(onClick = { isTextVisible = !isTextVisible }) {
                    Icon(
                        imageVector = icon,
                        contentDescription = stringResource(id = R.string.password_visibility_toggle),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    )
    AnimatedVisibility(
        visible = isError,
        enter = expandVertically(),
        exit = shrinkVertically()

    ) {
        Text(
            text = errorText,
            color = MaterialTheme.colorScheme.error,
            fontSize = 14.sp,
            lineHeight = 16.sp,
            modifier = Modifier.padding(top = 8.dp),
            fontWeight = FontWeight.Normal
        )
    }
}