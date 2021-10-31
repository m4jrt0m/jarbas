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

fun Array<String>.noteModeIndex(): Int = this.indexOf("--note")

fun Array<String>.todoModeIndex(): Int = this.indexOf("--todo")

fun Array<String>.folderIndex(): Int = this.indexOf("-f")

fun Array<String>.helpIndex(): Int = this.indexOf("-h")

fun Array<String>.fileNameIndex(): Int = this.indexOf("-o")

fun Array<String>.listIndex(): Int = this.indexOf("-l")

fun Array<String>.newIndex(): Int = this.indexOf("-n")

fun Array<String>.toArgs(): Args {
    var mode = Mode.None
    var folder = "."
    var isHelp = false
    var todo = TodoArgs()
    var note = NoteArgs()
    var fileName = ""

    if (this.todoModeIndex() != -1) {
        mode = this[this.todoModeIndex()].toModes()
    }

    if (this.noteModeIndex() != -1) {
        mode = this[this.noteModeIndex()].toModes()
    }

    if(this.folderIndex() != -1) {
        folder = this[this.folderIndex() + 1]
    }

    if(Help().isHelpNote(this.helpIndex(), mode) || Help().isHelpTodo(this.helpIndex(), mode) || Help().isHelp(this.helpIndex(), mode)){
        isHelp = true
    }

    if(this.fileNameIndex() != -1 && this.count() >= this.fileNameIndex() + 2) {
        fileName = this[this.fileNameIndex() + 1]
    }

    if(mode == Mode.Todo){
        if(this.newIndex() != -1) {
            val content = if (this.count() >= this.newIndex() + 2) this[this.newIndex() + 1] else ""
            todo = TodoArgs(isNew = true, content = content, fileName = fileName)
        }
        if(this.listIndex() != -1) {
            todo = TodoArgs(isList = true)
        }
    }
    if(mode == Mode.Note){
        if(this.newIndex() != -1) {
            val content = if (this.count() >= this.newIndex() + 2) this[this.newIndex() + 1] else ""
            note = NoteArgs(isNew = true, content = content, fileName = fileName)
        }
        if(this.listIndex() != -1) {
            note = NoteArgs(isList = true)
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
    return clone.trim().replace(" ", "").replace("\\", "").replace("/", "")
}

fun String.toMd5(): ByteArray = MessageDigest.getInstance("MD5").digest(this.toByteArray(UTF_8))

fun ByteArray.toHex(): String = joinToString(separator = "") { byte -> "%02x".format(byte) }