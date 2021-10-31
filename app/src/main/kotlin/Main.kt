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
            Mode.Todo -> Todo().processTodoOperation(input)
        }
    }
}