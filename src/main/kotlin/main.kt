abstract class Day {
    abstract fun task1(): Long
    abstract fun task2(): Long

    val inputLines: List<String> by lazy { inputString.lines() }
    val inputString: String by lazy { inputFile.readText() }

    private val inputFile = javaClass.getResource("${javaClass.name}.txt")
}

fun main() {
    val day = Day7()
    println(day.task1())
    println(day.task2())
}


