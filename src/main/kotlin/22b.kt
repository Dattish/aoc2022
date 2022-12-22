import java.io.File

fun day22b() {
    val (grid, ins) = File("22.txt")
        .readText()
        .split("\n\n")
        .map { it.split('\n') }
        .map { it.toMutableList() }
    val instructions = ins[0]

    val directions = listOf(-1 to 0, 0 to 1, 1 to 0, 0 to -1)
    val directionValue = mapOf(0 to 3, 1 to 0, 2 to 1, 3 to 2)
    val regions = listOf(0 to 1, 0 to 2, 1 to 1, 2 to 1, 2 to 0, 3 to 0)
    val cubeSideEdgeMapping = mapOf(
        Pair(4, 0) to Pair(3, 0), Pair(4, 1) to Pair(2, 3), Pair(4, 2) to Pair(6, 3), Pair(4, 3) to Pair(5, 3),
        Pair(1, 0) to Pair(6, 1), Pair(1, 1) to Pair(2, 1), Pair(1, 2) to Pair(3, 2), Pair(1, 3) to Pair(5, 1),
        Pair(3, 0) to Pair(1, 0), Pair(3, 1) to Pair(2, 0), Pair(3, 2) to Pair(4, 2), Pair(3, 3) to Pair(5, 2),
        Pair(6, 0) to Pair(5, 0), Pair(6, 1) to Pair(4, 0), Pair(6, 2) to Pair(2, 2), Pair(6, 3) to Pair(1, 2),
        Pair(2, 0) to Pair(6, 0), Pair(2, 1) to Pair(4, 3), Pair(2, 2) to Pair(3, 3), Pair(2, 3) to Pair(1, 3),
        Pair(5, 0) to Pair(3, 1), Pair(5, 1) to Pair(4, 1), Pair(5, 2) to Pair(6, 2), Pair(5, 3) to Pair(1, 1)
    )

    val rows = grid.size
    val columns = grid[0].length
    val cube = columns / 3

    val wall = '#'
    val path = '.'

    for (row in 0 until rows) {
        while (grid[row].length < columns) {
            grid[row] += " "
        }
    }

    fun getCubeSide(row: Int, column: Int): Triple<Int, Int, Int> {
        if (row < cube) {
            if (column < 2 * cube) {
                return Triple(1, row, column - cube)
            }
            return Triple(2, row, column - 2 * cube)
        } else if (row < 2 * cube) {
            return Triple(3, row - cube, column - cube)
        } else if (row < 3 * cube) {
            if (column < cube) {
                return Triple(5, row - 2 * cube, column)
            }
            return Triple(4, row - 2 * cube, column - cube)
        }
        return Triple(6, row - 3 * cube, column)
    }

    fun getLocalCoordinates(rowPosition: Int, columnPosition: Int, direction: Int, newDirection: Int): Pair<Int, Int> {
        val x = when (direction) {
            0 -> columnPosition
            1 -> rowPosition
            2 -> cube - 1 - columnPosition
            3 -> cube - 1 - rowPosition
            else -> throw RuntimeException("invalid direction $direction")
        }

        return when (newDirection) {
            0 -> cube - 1 to x
            1 -> x to 0
            2 -> 0 to cube - 1 - x
            3 -> cube - 1 - x to cube - 1
            else -> throw RuntimeException("invalid newDirection $newDirection")
        }
    }

    fun getGlobalCoordinates(localRow: Int, localColumn: Int, newRegion: Int): Pair<Int, Int> {
        val (regionRow, regionColumn) = regions[newRegion - 1]
        return regionRow * cube + localRow to regionColumn * cube + localColumn
    }

    fun cubeSideMove(row: Int, column: Int, direction: Int): Triple<Int, Int, Int> {
        val (cubeSide, rowPosition, columnPosition) = getCubeSide(row, column)
        val (newCubeSide, newDirection) = cubeSideEdgeMapping[cubeSide to direction]!!

        val (localRow, localColumn) = getLocalCoordinates(rowPosition, columnPosition, direction, newDirection)
        val (globalRow, globalColumn) = getGlobalCoordinates(localRow, localColumn, newCubeSide)

        return Triple(globalRow, globalColumn, newDirection)
    }

    var row = 0
    var column = 0
    var direction = 1

    while (grid[row][column] != path) {
        column++
    }

    var i = 0
    while (i < instructions.length) {
        var n = 0
        while (i < instructions.length && instructions[i].isDigit()) {
            n = n * 10 + instructions[i].digitToInt()
            i++
        }
        for (ignored in 0 until n) {
            val (rowMove, columnMove) = directions[direction]
            val normalRowMove = (row + rowMove).mod(rows)
            val normalColumnMove = (column + columnMove).mod(columns)

            if (grid[normalRowMove][normalColumnMove].isWhitespace()) {
                val (cubeSideCrossingRowPosition, cubeSideCrossingColumnPosition, cubeSideCrossingDirection) = cubeSideMove(row, column, direction)
                if (grid[cubeSideCrossingRowPosition][cubeSideCrossingColumnPosition] == wall) {
                    break
                }
                row = cubeSideCrossingRowPosition
                column = cubeSideCrossingColumnPosition
                direction = cubeSideCrossingDirection
            } else if (grid[normalRowMove][normalColumnMove] == wall) {
                break
            } else {
                row = normalRowMove
                column = normalColumnMove
            }
        }
        if (i >= instructions.length) {
            break
        }
        direction = when (instructions[i]) {
            'L' -> (direction + 3).mod(directions.size)
            'R' -> (direction + 1).mod(directions.size)
            else -> throw RuntimeException("unknown direction ${instructions[i]}")
        }
        i++
    }

    println((row + 1) * 1000 + (column + 1) * 4 + directionValue[direction]!!)
}
