import model.Args
import kotlin.random.Random

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