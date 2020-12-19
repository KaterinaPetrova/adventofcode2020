class Day7 : Day() {

    override fun task1() = allPossibleOutMostBags("shiny gold").size.toLong()

    override fun task2() = allNestedBagsCount("shiny gold").toLong()

    private val bags: List<Bag> = inputLines.map(Bag::fromString)

    private fun allNestedBagsCount(myBag: String): Int =
        bags.find { it.name.contains(myBag) }?.nested?.entries?.sumBy { it.value + allNestedBagsCount(it.key) * it.value }
            ?: 0

    private fun allPossibleOutMostBags(myBag: String): List<Bag> =
        bags.filter { bag ->
            bag.nested.keys.exists { it.contains(myBag) }
        }.let { bags -> bags.map { allPossibleOutMostBags(it.name) }.flatten().plus(bags).distinct() }

    class Bag(val name: String, val nested: Map<String, Int>) {
        companion object {
            private const val ruleDelimiter  = " bags contain "
            fun fromString(string: String): Bag {
                val name = string.substringBefore(ruleDelimiter)
                val nested = string.substringAfter(ruleDelimiter)
                    .dropLast(1)
                    .split(", ")
                    .associate {
                        it.substringBefore(" bag").substringAfter(' ') to (it.substringBefore(' ').toIntOrNull() ?: 0)
                    }
                return Bag(name, nested)
            }
        }
    }
}
