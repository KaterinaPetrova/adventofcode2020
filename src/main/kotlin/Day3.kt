class Day3 : Day() {

    override fun task1() = calculateTreesForSlope(slopes[1])

    override fun task2() = slopes.map { calculateTreesForSlope(it) }.reduce { acc, i -> acc * i }

    private val slopes = listOf(Pair(1, 1), Pair(3, 1), Pair(5, 1), Pair(7, 1), Pair(1, 2))

    private fun calculateTreesForSlope(slope: Pair<Int, Int>) =
        (inputLines.indices step slope.second)
            .sumBy { i -> if (inputLines[i][i / slope.second * slope.first % inputLines[i].length] == '#') 1 else 0 }
            .toLong()
}
