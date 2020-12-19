import java.util.*
import kotlin.math.absoluteValue

class Day12 : Day() {

    override fun task1(): Long {
        var position = Position(0, 0)
        var currentDirection = Command.Direction.EAST
        inputLines.forEach {
            val move = Command.fromString(it).run(currentDirection)
            currentDirection = move.first
            position += move.second
        }
        return (position.first.absoluteValue + position.second.absoluteValue).toLong()
    }

    override fun task2(): Long {
        var position = Position(0, 0)
        var currentWayPoint = Pair(10, -1)
        inputLines.forEach {
            val move = Command.fromString(it).run(currentWayPoint)
            position += move.ship
            currentWayPoint = move.wayPoint
        }
        return (position.first.absoluteValue + position.second.absoluteValue).toLong()
    }

    class Command(private val direction: Direction, private val value: Int) {

        enum class Direction {
            EAST, WEST, NORTH, SOUTH, RIGHT, LEFT, FORWARD;

            fun rotate(angle: Int, right: Boolean): Direction {
                val all = listOf(Direction.EAST, Direction.SOUTH, Direction.WEST, Direction.NORTH)
                Collections.rotate(all, (angle / 90) * (if (right) -1 else 1) - all.indexOf(this))
                return all.first()
            }
        }

        class Move(val ship: Position, val wayPoint: Position)

        fun run(wayPoint: Position) = when (direction) {
            Direction.EAST -> Move(Position(0, 0), Position(wayPoint.first + value, wayPoint.second))
            Direction.WEST -> Move(Position(0, 0), Position(wayPoint.first - value, wayPoint.second))
            Direction.SOUTH -> Move(Position(0, 0), Position(wayPoint.first, wayPoint.second + value))
            Direction.NORTH -> Move(Position(0, 0), Position(wayPoint.first, wayPoint.second - value))
            Direction.RIGHT -> Move(Position(0, 0), wayPoint.rotate(value, true))
            Direction.LEFT -> Move(Position(0, 0), wayPoint.rotate(value, false))
            Direction.FORWARD -> Move(Pair(wayPoint.first * value, wayPoint.second * value), wayPoint)
        }

        fun run(fromDirection: Direction): Pair<Direction, Position> = when (direction) {
            Direction.EAST -> Pair(fromDirection, Position(value, 0))
            Direction.WEST -> Pair(fromDirection, Position(-value, 0))
            Direction.SOUTH -> Pair(fromDirection, Position(0, value))
            Direction.NORTH -> Pair(fromDirection, Position(0, -value))
            Direction.RIGHT -> Pair(fromDirection.rotate(value, true), Position(0, 0))
            Direction.LEFT -> Pair(fromDirection.rotate(value, false), Position(0, 0))
            Direction.FORWARD -> (Command(fromDirection, value)).run(fromDirection)
        }

        companion object {
            fun fromString(string: String): Command {
                val direction = when (string[0]) {
                    'N' -> Direction.NORTH
                    'E' -> Direction.EAST
                    'W' -> Direction.WEST
                    'S' -> Direction.SOUTH
                    'R' -> Direction.RIGHT
                    'L' -> Direction.LEFT
                    'F' -> Direction.FORWARD
                    else -> throw Exception("Wrong input")
                }
                return Command(direction, string.drop(1).toInt())
            }
        }
    }
}

typealias Position = Pair<Int, Int>

operator fun Position.plus(other: Position) = Position(this.first + other.first, this.second + other.second)

fun Position.rotate(angle: Int, clockwise: Boolean): Position {
    var (x, y) = this
    repeat(angle / 90) {
        x = if (clockwise) -y.also { y = x } else y.also { y = -x }
    }
    return Position(x, y)
}