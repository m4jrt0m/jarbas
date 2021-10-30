fun main(args: Array<String>) {
    var currentDir = "."
    var currentMode: Modes = Modes.None

    args.forEachIndexed{ index, it ->
        if(it.isModes()) {
            currentMode = it.toModes()
        }

        if(it == "-f") {
            currentDir = args[index + 1]
        }

        if(Help().isHelpNote(it, currentMode)){
            Help().helpNote(currentDir)
        }

        if(Help().isHelpTodo(it, currentMode)){
            Help().helpTodo(currentDir)
        }

        if(Help().isHelp(it, currentMode)) {
            Help().help(currentDir)
        }
    }
}