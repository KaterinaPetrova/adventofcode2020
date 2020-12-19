import kotlin.math.min
import kotlin.math.max

class Day11 : Day() {

    private val planeMap: Array<Array<Place>>
        get() {
            val lines = inputLines
            var map: Array<Array<Place>> = Array(lines.size) { Array(lines.first().length) { Place.FLOOR } }
            lines.withIndex().forEach { line ->
                line.value.withIndex().forEach { row ->
                    map[line.index][row.index] = when (row.value) {
                        '.' -> Place.FLOOR
                        'L' -> Place.EMPTY
                        '#' -> Place.OCCUPATED
                        else -> throw Exception("Wrong input")
                    }
                }
            }
            return map
        }

    override fun task1(): Long {
        var map = planeMap
        var hasChange = true
        while (hasChange) {
            hasChange = false
            val newMap: Array<Array<Place>> = Array(map.size) { Array(map.first().size) { Place.EMPTY } }
            for (i in map.indices) {
                for (j in map[i].indices) {
                    newMap[i][j] = newValueForPlace(Pair(i, j), map) ?: map[i][j]
                    hasChange = hasChange || newMap[i][j] != map[i][j]
                }
            }
            map = newMap
        }
        return map.flatten().count { it == Place.OCCUPATED }.toLong()
    }

    private fun newValueForPlace(place: Pair<Int, Int>, map: Array<Array<Place>>): Place? {
        val occupatedCount = numberOfOccupatedForPlace(place, map)
        val place = map[place.first][place.second]
        return if (place == Place.EMPTY && occupatedCount == 0) Place.OCCUPATED
        else if (place == Place.OCCUPATED && occupatedCount >= 4) Place.EMPTY
        else null
    }

    private fun newValueForPlace2(place: Pair<Int, Int>, map: Array<Array<Place>>): Place? {
        val occupatedCount = numberOfOccupatedForPlace2(place, map)
        val place = map[place.first][place.second]
        return if (place == Place.EMPTY && occupatedCount == 0) Place.OCCUPATED
        else if (place == Place.OCCUPATED && occupatedCount >= 5) Place.EMPTY
        else null
    }

    private fun numberOfOccupatedForPlace(place: Pair<Int, Int>, map: Array<Array<Place>>): Int {
        var occupatedCount = 0
        for (y in max(0, place.first - 1)..min(map.size - 1, place.first + 1)) {
            for (z in max(0, place.second - 1)..min(map[place.first].size - 1, place.second + 1)) {
                if (map[y][z] == Place.OCCUPATED && !(y == place.first && z == place.second)) {
                    occupatedCount++
                }
            }
        }
        return occupatedCount
    }

    private fun numberOfOccupatedForPlace2(place: Pair<Int, Int>, map: Array<Array<Place>>): Int = allDirections().count { isDirectionOccupated(place, map, it) }

    private fun isDirectionOccupated(place: Pair<Int, Int>, map: Array<Array<Place>>, direction: Direction): Boolean {
        var (x, y) = place
        x = direction.xChange(x)
        y = direction.yChange(y)
        var nearestPlace: Place = Place.FLOOR
        while (x >= 0 && x < map.size && y >= 0 && y < map[y].size  && nearestPlace == Place.FLOOR) {
            nearestPlace = map[x][y]
            x = direction.xChange(x)
            y = direction.yChange(y)
        }
        return nearestPlace == Place.OCCUPATED
    }

    private fun allDirections(): List<Direction> = listOf(
        Direction( { it - 1 }, { it - 1}),
        Direction( { it + 1}, { it + 1 }),
        Direction( { it }, { it - 1}),
        Direction( { it }, { it + 1 }),
        Direction( { it - 1 }, { it }),
        Direction( { it + 1}, { it }),
        Direction( { it - 1 }, { it + 1}),
        Direction( { it + 1 }, { it - 1})
    )

    class Direction(val xChange: ( Int ) -> Int, val yChange: ( Int ) -> Int)

    override fun task2(): Long {
        var map = planeMap
        var hasChange = true
        while (hasChange) {
            hasChange = false
            val newMap: Array<Array<Place>> = Array(map.size) { Array(map.first().size) { Place.EMPTY } }
            for (i in map.indices) {
                for (j in map[i].indices) {
                    newMap[i][j] = newValueForPlace2(Pair(i, j), map) ?: map[i][j]
                    hasChange = hasChange || newMap[i][j] != map[i][j]
                }
            }
            map = newMap
        }
        return map.flatten().count { it == Place.OCCUPATED }.toLong()
    }

    enum class Place {
        FLOOR,
        EMPTY,
        OCCUPATED
    }
}