import java.io.File

fun day22a() {
    val (grid, ins) = File("22.txt")
        .readText()
        .split("\n\n")
        .map { it.split('\n') }
        .map { it.toMutableList() }
    val instructions = ins[0]

    val directions = listOf(-1 to 0, 0 to 1, 1 to 0, 0 to -1)
    val directionValue = mapOf(0 to 3, 1 to 0, 2 to 1, 3 to 2)

    val rows = grid.size
    val columns = grid[0].length

    val wall = '#'
    val path = '.'

    for (row in 0 until rows) {
        while (grid[row].length < columns) {
            grid[row] += " "
        }
    }

    fun wrappingMove(row: Int, column: Int, direction: Int): Pair<Int, Int> {
        val (rowMove, columnMove) = directions[direction]
        var r = (row + rowMove).mod(rows)
        var c = (column + columnMove).mod(columns)
        while (grid[r][c].isWhitespace()) {
            r = (r + rowMove).mod(rows)
            c = (c + columnMove).mod(columns)
        }
        return r to c
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
                val (wrappingRowMove, wrappingColumnMove) = wrappingMove(row, column, direction)
                if (grid[wrappingRowMove][wrappingColumnMove] == wall) {
                    break
                }
                row = wrappingRowMove
                column = wrappingColumnMove
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
