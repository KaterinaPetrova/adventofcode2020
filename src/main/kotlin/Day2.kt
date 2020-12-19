class Day2 : Day() {

    private fun getPasswordRecords() = inputLines
        .map { it.split(" ") }
        .map {
            val occurrences = it[0].split("-")
            PasswordRecord(
                occurrences[0].toInt(),
                occurrences[1].toInt(),
                it[1][0],
                it[2]
            )
        }

    override fun task1() = getPasswordRecords().count { it.isCorrect() }.toLong()

    override fun task2() = getPasswordRecords().count { it.isCorrect2() }.toLong()

    data class PasswordRecord(
        val minOccurrence: Int,
        val maxOccurrence: Int,
        val letter: Char,
        val password: String
    ) {
        fun isCorrect() = password.count { it == letter } in minOccurrence..maxOccurrence
        fun isCorrect2() =
            password.hasAt(minOccurrence - 1, letter) xor password.hasAt(maxOccurrence - 1, letter)

        private fun String.hasAt(index: Int, char: Char): Boolean = index < length && this[index] == char
    }
}

