fun String.toModes(): Modes =
    when (this) {
        "--todo"    -> Modes.Todo
        "--note"    -> Modes.Note
        else        -> Modes.None
    }

fun String.isModes(): Boolean =
    when (this) {
        "--todo"    -> true
        "--note"    -> true
        else        -> false
    }