class Day6 : Day() {

    override fun task1() = mergedAnswers.sumBy { it.toSet().count() }.toLong()

    override fun task2() = answers.sumBy { it.commonCharacters().size }.toLong()

    private val rawAnswers = inputString.split("\n\n")

    private val mergedAnswers = rawAnswers.map { it.replace("\n", "") }

    private val answers = rawAnswers.map { it.lines() }

    private fun List<String>.commonCharacters() = joinToString("").groupBy { it }.filter { it.value.size == size }

}