import java.io.File

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