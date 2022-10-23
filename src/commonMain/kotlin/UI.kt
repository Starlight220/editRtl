package io.starlight220.editrtl

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.TextFieldDefaults.textFieldColors
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.isCtrlPressed
import androidx.compose.ui.input.key.key
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun Root(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = darkColors()
    ) {
        CompositionLocalProvider(
        LocalLayoutDirection provides LayoutDirection.Rtl
    ) {
        content()
    }

    }
}

@Composable
fun TextEditor(contentState: MutableState<String> = remember { mutableStateOf("") }) {
    val (value, onValueChange) = contentState
    TextField(
        modifier = Modifier.fillMaxSize().padding(10.dp),
        value = value, onValueChange = onValueChange,
        colors = textFieldColors(backgroundColor = Color.Black, textColor = Color.Cyan),
//        textStyle = TextStyle.Default.copy(textDirection = TextDirection.Rtl)
    )
}

@OptIn(ExperimentalComposeUiApi::class)
fun CoroutineScope.handleKeyPress(
    filepath: String,
    content: String,
    keyEvent: KeyEvent
): Boolean = if (keyEvent.isCtrlPressed && keyEvent.key == Key.S) {
    launch(Dispatchers.IO) {
        save(filepath, content)
    }
    true
} else false

expect fun save(filepath: String, content: String)
