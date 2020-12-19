import kotlin.math.min
import kotlin.math.max


class Day13 : Day() {

    override fun task1(): Long {
        val input = inputLines
        val departmentTime = input[0].toInt()
        val bus = inputLines[1]
            .split(',')
            .mapNotNull { it.toIntOrNull() }
            .map { Pair(it, it - departmentTime % it) }
            .minBy { it.second }!!
        return (bus.first * bus.second).toLong()
    }

    override fun task2(): Long {
        TODO()
    }

}