package model

data class NoteArgs(
    val isNew: Boolean = false,
    val isList: Boolean = false,
    val content: String = "",
    val fileName: String = ""
)