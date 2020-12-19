import java.io.File

class Day16 : Day() {

    override fun task1() = tickets.flatten().filter { field -> !rules.exists { it.isFieldValid(field) } }.sum().toLong()

    override fun task2(): Long {
        val goodTickets = tickets.filter(::isTicketValid)
        var indexedRules =
            goodTickets.first().indices.asSequence().map { i -> goodTickets.indices.map { goodTickets[it][i] } }
                .map { t -> rules.filter { it.isFieldsValid(t) }.toMutableList() }.withIndex()
                .sortedBy { it.value.size }
                .toList()
        indexedRules.indices.forEach { i ->
            (i + 1 until indexedRules.size).forEach {
                indexedRules[it].value.remove(indexedRules[i].value.first())
            }
        }
        val indexes = indexedRules.filter { it.value.first()!!.name.contains("departure") }.map { it.index }
        return myTicket.filterIndexed { i, _ -> indexes.contains(i) }.map { it.toLong() }.reduce { acc, i -> acc * i }
    }

    private fun isTicketValid(fields: List<Int>) = !fields.exists { f -> !rules.exists { it.isFieldValid(f) } }

    private val rawData = inputString.split("\n\n")
    private val rules: List<Rule> = rawData[0].lines().map { Rule.fromString(it) }

    private val myTicket: List<Int> = rawData[1].lines().last().split(',').map { it.toInt() }
    private val tickets: List<List<Int>> = rawData[2].lines().drop(1).map { s -> s.split(',').map { it.toInt() } }

    class Rule(val name: String, private val ranges: List<IntRange>) {

        fun isFieldValid(field: Int) = ranges.exists { it.contains(field) }
        fun isFieldsValid(fields: List<Int>) = !fields.exists { !isFieldValid(it) }

        companion object {
            fun fromString(string: String) = Rule(string.substringBefore(":"), rangesFromString(string))

            private fun rangesFromString(string: String) =
                Regex("[0-9]+-[0-9]+").findAll(string).map { rangeFromString(it.value) }
                    .toList()

            private fun rangeFromString(string: String) =
                string.substringBefore("-").toInt()..string.substringAfter("-").toInt()
        }
    }
}

inline fun <T> Iterable<T>.exists(predicate: (T) -> Boolean): Boolean {
    return find(predicate) != null
}


