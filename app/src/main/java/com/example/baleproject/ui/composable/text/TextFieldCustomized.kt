package com.example.baleproject.ui.composable.text

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.baleproject.ui.composable.text.keybaordaction.KeyboardAction
import com.example.baleproject.ui.theme.blue

@Composable
fun TextFieldCustomized(
    value: String,
    hint: String,
    modifier: Modifier = Modifier,
    color: Color = blue,
    readOnly: Boolean = false,
    isEnable: Boolean = true,
    hasError: Boolean = false,
    label: String? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardAction: KeyboardAction = KeyboardAction.Default,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        isError = hasError,
        enabled = isEnable,
        onValueChange = onValueChange,
        readOnly = readOnly,
        modifier = Modifier
            .padding(top = 10.dp)
            .fillMaxWidth()
            .then(modifier),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            cursorColor = color,
            focusedBorderColor = color,
            focusedLabelColor = color,
        ),
        placeholder = {
            Text(text = hint, color = Color.Gray)
        },
        label = {
            label?.let {
                Text(text = hint, color = blue)
            }
        },
        trailingIcon = trailingIcon,
        visualTransformation = visualTransformation,
        shape = RoundedCornerShape(10.dp),
        keyboardActions = keyboardAction.action(),
        keyboardOptions = keyboardAction.option(),
    )
}

@Composable
fun TextFieldCustomized(
    state: TextFieldCustomizedState,
    hint: String,
    modifier: Modifier = Modifier,
    color: Color = blue,
    label: String? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardAction: KeyboardAction = KeyboardAction.Default,
) {
    TextFieldCustomized(
        hint = hint,
        label = label,
        value = state.value,
        isEnable = state.isEnable,
        hasError = state.hasError,
        onValueChange = state.onValueChanged,
        modifier = modifier,
        color = color,
        trailingIcon = trailingIcon,
        visualTransformation = visualTransformation,
        keyboardAction = keyboardAction,
    )
}


data class TextFieldCustomizedState(
    val value: String,
    val onValueChanged: (String) -> Unit,
    val isEnable: Boolean = true,
    val hasError: Boolean = false,
) {
    fun isValid(): Boolean {
        return hasError.not() and value.isNotBlank()
    }
}