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

    fun isHelpNote(arg: String, currentMode: Mode): Boolean = arg == "-h" && currentMode == Mode.Note

    fun isHelpTodo(arg: String, currentMode: Mode): Boolean = arg == "-h" && currentMode == Mode.Todo

    fun isHelp(arg: String, currentMode: Mode): Boolean = arg == "-h" && currentMode == Mode.None
}