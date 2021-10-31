package model

import Mode

data class Args(
    val folder: String = "",
    val mode: Mode = Mode.None,
    val note: NoteArgs = NoteArgs(),
    val todo: TodoArgs = TodoArgs(),
    val isHelp: Boolean = false
)
