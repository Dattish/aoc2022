import java.io.File

fun day24b() {
    data class Direction(val x: Int, val y: Int)
    data class Movement(val x: Int, val y: Int)
    data class Coordinate(val x: Int, val y: Int) {
        fun moveInDirection(direction: Direction, time: Int, maxX: Int, maxY: Int): Coordinate {
            return Coordinate((x + time * direction.x).mod(maxX), (y + time * direction.y).mod(maxY))
        }

        fun move(movement: Movement): Coordinate {
            return Coordinate(x + movement.x, y + movement.y)
        }
    }

    val walls = mutableSetOf<Coordinate>()
    val blizzards = mutableSetOf<Pair<Coordinate, Direction>>()

    val neighborMoves = listOf(
        Movement(1, 0), Movement(0, 1), Movement(-1, 0), Movement(0, -1), Movement(0, 0)
    )

    val lines = File("24.txt").readLines()

    lines.forEachIndexed { y, line ->
        line.forEachIndexed { x, char ->
            when (char) {
                '#' -> walls.add(Coordinate(x - 1, y - 1))
                '>' -> blizzards.add(Coordinate(x - 1, y - 1) to Direction(1, 0))
                'v' -> blizzards.add(Coordinate(x - 1, y - 1) to Direction(0, 1))
                '<' -> blizzards.add(Coordinate(x - 1, y - 1) to Direction(-1, 0))
                '^' -> blizzards.add(Coordinate(x - 1, y - 1) to Direction(0, -1))
                else -> {
                    // don't do anything, just make the compiler happy
                }
            }
        }
    }

    val maxX = lines.first().length - 2
    val maxY = lines.size - 2
    (-1 until 3).map { Coordinate(it, -2) }.forEach { walls.add(it) }
    (maxX - 3 until maxX + 2).map { Coordinate(it, maxY + 1) }.forEach { walls.add(it) }
    val start = Coordinate(0, -1)
    val endCoordinate = Coordinate(maxX - 1, maxY)

    var time = 0
    var possiblePositions = setOf(start)

    val end = ArrayDeque<Coordinate>().apply {
        add(endCoordinate)
        add(start)
        add(endCoordinate)
    }
    while (end.isNotEmpty()) {
        time++
        val movedBlizzards = blizzards.map { (coordinate, direction) -> coordinate.moveInDirection(direction, time, maxX, maxY) }.toSet()
        val neighbors = possiblePositions.flatMap { position -> neighborMoves.map { position.move(it) } }.toSet()
        possiblePositions = ((neighbors - walls) - movedBlizzards)
        if (end.first() in possiblePositions) {
            possiblePositions = setOf(end.removeFirst())
        }
    }
    println(time)
}