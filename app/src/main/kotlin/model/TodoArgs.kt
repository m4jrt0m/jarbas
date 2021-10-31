package model

data class TodoArgs(
    val isNew: Boolean = false,
    val isList: Boolean = false,
    val content: String = "",
    val fileName: String = ""
)
