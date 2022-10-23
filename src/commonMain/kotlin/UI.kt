package io.starlight220.editrtl

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.isCtrlPressed
import androidx.compose.ui.input.key.key
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun TextEditor(contentState: MutableState<String> = remember { mutableStateOf("") }) {
    val (value, onValueChange) = contentState
    BasicTextField(
        modifier = Modifier.fillMaxSize().padding(10.dp),
        value = value, onValueChange = onValueChange,
        textStyle = TextStyle.Default.copy(textDirection = TextDirection.Rtl)
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
