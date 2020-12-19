class Day5 : Day() {
    override fun task1() = ids.max()!!.toLong()

    override fun task2() = ids.sorted().withIndex().toList().let { ids ->
        ids.find { ids[it.index + 1].value - it.value > 1 }!!.value.toLong() + 1
    }

    private val ids = inputLines.map(::idFromPassCode)

    private fun idFromPassCode(code: String): Int {
        val row = numberFromPassCode(code.dropLast(3), Pair('F', 'B'))
        val sit = numberFromPassCode(code.drop(7), Pair('L', 'R'))
        return row * 8 + sit
    }

    private fun numberFromPassCode(string: String, code: Pair<Char, Char>) =
        string.map { if (it == code.first) '0' else '1' }.joinToString("").toInt(2)
}