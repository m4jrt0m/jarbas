class Todo {

    fun New(todo: String, currentDir: String) = FileOperations().Write(todo, currentDir, "test.md")

}