import model.Args
import kotlin.random.Random

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