class Day8 : Day() {
    override fun task1() = execute().acc

    override fun task2() = execute().history.map { execute(corruptedLine = it) }.find { it.terminated }!!.acc

    private fun execute(commands: List<String> = inputLines, corruptedLine: Int? = null): ProgramExecutionResult {
        var acc = 0
        var lineIndex = 0
        val history = mutableListOf<Int>()
        while (lineIndex < commands.size) {
            if (history.contains(lineIndex)) break
            history.add(lineIndex)
            val command = commands[lineIndex].split(" ")
            val operator = command.first()
            val argument = command.last().toInt()
            val lineIsCorrupted = lineIndex == corruptedLine
            when {
                operator == "acc" -> acc += argument.also { lineIndex++ }
                (operator == "jmp" && !lineIsCorrupted) || (operator == "nop" && lineIsCorrupted) -> lineIndex += argument
                (operator == "nop" && !lineIsCorrupted) || (operator == "jmp" && lineIsCorrupted) -> lineIndex++
            }
        }
        return ProgramExecutionResult(acc.toLong(), history, lineIndex == commands.size)
    }

    class ProgramExecutionResult(val acc: Long, val history: List<Int>, val terminated: Boolean)
}