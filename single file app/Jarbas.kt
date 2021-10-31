import java.security.MessageDigest
import kotlin.text.Charsets.UTF_8
import java.io.File
import kotlin.random.Random


enum class Mode {
    None,
    Note,
    Todo,
}

data class Args(
    val folder: String = "",
    val mode: Mode = Mode.None,
    val note: NoteArgs = NoteArgs(),
    val todo: TodoArgs = TodoArgs(),
    val isHelp: Boolean = false
)

data class NoteArgs(
    val isNew: Boolean = false,
    val isList: Boolean = false,
    val content: String = "",
    val fileName: String = ""
)

data class TodoArgs(
    val isNew: Boolean = false,
    val isList: Boolean = false,
    val content: String = "",
    val fileName: String = ""
)

class FileOperations {

    fun write(content: String, folder: String, fileName: String) {
        val file = File("$folder/$fileName")
        if(!file.isFile){
            file.createNewFile()
        }
        file.writeText(content)
    }

    fun list(folder: String): List<File> = File(folder).walkTopDown().toList()
}

class Help {

    fun help(current_dir: String) {
        println("##### Jarbas #####")
        println()
        println("How may I help you sir,")
        println()
        println("current dir: $current_dir")
        println()
        println(" -f set the folder")
        println(" --todo switch to todo mode")
        println(" --note switch to note mode")
        println()
        println("example:")
        println("jarbas -f ~/Desktop")
    }
    // Todo :: Add git sync


    fun helpNote(current_dir: String) {
        println("##### Jarbas[Note] #####")
        println()
        println("current dir: $current_dir")
        println()
        println(" -n add new note")
        println(" -l list notes")
        println(" -o file name")
    }


    fun helpTodo(current_dir: String) {
        println("##### Jarbas[Todo] #####")
        println()
        println("current dir: $current_dir")
        println()
        println(" -n add new todo")
        println(" -l list todos")
        println(" -o file name")
    }

    fun isHelpNote(helpIndex: Int, currentMode: Mode): Boolean = helpIndex != -1 && currentMode == Mode.Note

    fun isHelpTodo(helpIndex: Int, currentMode: Mode): Boolean = helpIndex != -1 && currentMode == Mode.Todo

    fun isHelp(helpIndex: Int, currentMode: Mode): Boolean = helpIndex != -1 && currentMode == Mode.None
}

class Todo {

    fun processTodoOperation(input: Args) {
        if (input.todo.isNew) {
            if(input.todo.fileName.isNotEmpty()) {
                new(input.todo.content, input.folder, input.todo.fileName.sanitizeFileName())
            } else {
                new(input.todo.content, input.folder, Random.nextInt(10000, 99999).toString().toMd5().toHex().sanitizeFileName())
            }
        }
        if (input.todo.isList) {
            list(input.folder)
        }
    }

    private fun new(todo: String, currentDir: String, fileName: String) = FileOperations().write(todo, currentDir, fileName)

    private fun list(currentDir: String) = FileOperations().list(currentDir).forEach {
        if(it.name.contains(".md")) {
            println(it)
        }
    }
}

class Note {

    fun processNoteOperation(input: Args) {
        if (input.note.isNew) {
            if(input.note.fileName.isNotEmpty()) {
                new(input.note.content, input.folder, input.note.fileName.sanitizeFileName())
            } else {
                new(input.note.content, input.folder, Random.nextInt(10000, 99999).toString().toMd5().toHex().sanitizeFileName())
            }
        }
        if (input.note.isList) {
            list(input.folder)
        }
    }

    private fun new(note: String, currentDir: String, fileName: String) = FileOperations().write(note, currentDir, fileName)

    private fun list(currentDir: String) = FileOperations().list(currentDir).forEach {
        if(it.name.contains(".md")) {
            println(it)
        }
    }
}

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

fun main(args: Array<String>) {
    val input = args.toArgs()

    if (input.isHelp) {
        when (input.mode) {
            Mode.Note -> Help().helpNote(input.folder)
            Mode.Todo -> Help().helpTodo(input.folder)
            Mode.None -> Help().help(input.folder)
        }
    } else {
        when (input.mode) {
            Mode.Todo   -> Todo().processTodoOperation(input)
            Mode.Note   -> Note().processNoteOperation(input)
            else        -> return
        }
    }
}