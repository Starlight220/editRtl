package io.starlight220.editrtl

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.launchApplication
import kotlinx.coroutines.runBlocking
import java.awt.FileDialog
import java.io.File

fun main(args: Array<String>): Unit = runBlocking {
    launchApplication {
        val filepathState = remember { mutableStateOf("") }
        val contentState = remember { mutableStateOf("") }

        Window(
            state = WindowState(WindowPlacement.Maximized),
            title = "EditRtl: ${filepathState.value}",
            icon = painterResource("icon.svg"),
            onCloseRequest = {
                save(filepathState.value, contentState.value)
                exitApplication()
            },
            onKeyEvent = { handleKeyPress(filepathState.value, contentState.value, it) }
        ) {
            Root {
                fun openFile() {
                    args.getOrNull(0)?.let {
                        println("Got path <$it> from args")
                        filepathState.value = it
                        return
                    }
                    FileDialog(window).apply {
                        isMultipleMode = false
                        mode = FileDialog.LOAD
                        isVisible = true
                    }.run { (directory ?: return@run null) + (file ?: return@run null) }?.let {
                        println("Got path <$it> from dialog")
                        filepathState.value = it
                        return
                    }
                    error("No file selected")
                }
                fun loadContent(file: File) {
                    contentState.value = file.readText()
                }
                if (filepathState.value.isEmpty()) {
                    openFile()
                }
                loadContent(File(filepathState.value))
                TextEditor(contentState)
            }
        }
    }
}


actual fun save(filepath: String, content: String) {
    println("Saving to $filepath:\n```\n$content\n```")
    File(filepath).run {
        when {
            !exists() -> error("File $filepath doesn't exist!")
            !canWrite() -> error("Can't write to $filepath!")
            else -> writeText(content.intern())
        }
    }
}
