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


    fun helpNote(current_dir: String) {
        println("##### Jarbas[Note] #####")
        println()
        println("current dir: $current_dir")
        println()
        println(" -n add new note")
        println(" -l list notes")
        println(" -r remove note")
    }


    fun helpTodo(current_dir: String) {
        println("##### Jarbas[Todo] #####")
        println()
        println("current dir: $current_dir")
        println()
        println(" -n add new todo")
        println(" -l list todos")
        println(" -r remove todo")
    }

    fun isHelpNote(arg: String, currentMode: Modes): Boolean = arg == "-h" && currentMode == Modes.Note

    fun isHelpTodo(arg: String, currentMode: Modes): Boolean = arg == "-h" && currentMode == Modes.Todo

    fun isHelp(arg: String, currentMode: Modes): Boolean = arg == "-h" && currentMode == Modes.None
}