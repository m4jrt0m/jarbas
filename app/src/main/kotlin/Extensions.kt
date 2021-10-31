import model.Args
import model.NoteArgs
import model.TodoArgs
import java.security.MessageDigest
import kotlin.text.Charsets.UTF_8

fun String.toModes(): Mode =
    when (this) {
        "--todo"    -> Mode.Todo
        "--note"    -> Mode.Note
        else        -> Mode.None
    }

fun String.isModes(): Boolean =
    when (this) {
        "--todo"    -> true
        "--note"    -> true
        else        -> false
    }

fun Array<String>.toArgs(): Args {
    var mode = Mode.None
    var folder = "."
    var isHelp = false
    var todo = TodoArgs()
    var note = NoteArgs()

    this.forEachIndexed{ index, it ->
        if(it.isModes()) {
            mode = it.toModes()
        }
        if(it == "-f") {
            folder = this[index + 1]
        }
        if(Help().isHelpNote(it, mode) || Help().isHelpTodo(it, mode) || Help().isHelp(it, mode)){
            isHelp = true
        }
        if(mode == Mode.Todo){
            if(it == "-n") {
                var fileName = ""
                if(it == "-o") {
                    fileName = this[index + 1]
                }
                todo = TodoArgs(isNew = true, content = this[index + 1], fileName = fileName)
            }
            if(it == "-l") {
                todo = TodoArgs(isList = true)
            }
        }
        if(mode == Mode.Note){
            if(it == "-n") {
                var fileName = ""
                if(it == "-o") {
                    fileName = this[index + 1]
                }
                note = NoteArgs(isNew = true, content = this[index + 1], fileName = fileName)
            }
            if(it == "-l") {
                note = NoteArgs(isList = true)
            }
        }
    }

    return Args(
        folder,
        mode,
        note,
        todo,
        isHelp
    )
}

fun String.sanitizeFileName(): String {
    var clone = this
    if (!clone.contains(".md")) {
        clone += ".md"
    }
    return clone.trim()
}

fun String.toMd5(): ByteArray = MessageDigest.getInstance("MD5").digest(this.toByteArray(UTF_8))

fun ByteArray.toHex(): String = joinToString(separator = "") { byte -> "%02x".format(byte) }