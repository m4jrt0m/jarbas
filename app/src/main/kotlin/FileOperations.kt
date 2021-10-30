import java.io.File

class FileOperations {

    fun Write(content: String, folder: String, fileName: String) {
        val file = File(folder + fileName)
        if(file.isFile && file.canWrite()){
            file.writeText(content)
        }
    }
}