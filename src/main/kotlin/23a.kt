import java.io.File

fun day23a() {
    val pointsWithAnElf = File("23.txt").readLines()
        .flatMapIndexed { y, row -> row.mapIndexedNotNull { x, value -> if (value == '#') Pair(y, x) else null } }
        .toMutableSet()

    data class Movements(val left: Pair<Int, Int>, val center: Pair<Int, Int>, val right: Pair<Int, Int>)
    data class Direction(val left: Pair<Int, Int>, val center: Pair<Int, Int>, val right: Pair<Int, Int>) {
        fun toMovements(y: Int, x: Int): Movements {
            return Movements(
                y + left.first to x + left.second,
                y + center.first to x + center.second,
                y + right.first to x + right.second,
            )
        }
    }

    operator fun MutableSet<Pair<Int, Int>>.contains(movements: Movements): Boolean {
        return contains(movements.left) || contains(movements.center) || contains(movements.right)
    }

    val directions = mutableListOf(
        Direction(-1 to -1, -1 to 0, -1 to 1),
        Direction(1 to 1, 1 to 0, 1 to -1),
        Direction(1 to -1, 0 to -1, -1 to -1),
        Direction(-1 to 1, 0 to 1, 1 to 1),
    )

    for (ignored in 0 until 10) {
        val proposals = mutableMapOf<Pair<Int, Int>, MutableList<Pair<Int, Int>>>()
        for ((y, x) in pointsWithAnElf) {
            val movements = directions.map { it.toMovements(y, x) }
            for (movement in movements.filter { it !in pointsWithAnElf }) {
                val proposal = proposals[movement.center] ?: mutableListOf()
                proposal.add(y to x)
                proposals[movement.center] = proposal
                break
            }
        }

        proposals.filter { it.value.size == 1 }.forEach { (point, candidates) ->
            pointsWithAnElf.remove(candidates.first())
            pointsWithAnElf.add(point)
        }
        directions.add(directions.removeFirst())
    }
    val minY = pointsWithAnElf.minOf { it.first }
    val minX = pointsWithAnElf.minOf { it.second }
    val maxY = pointsWithAnElf.maxOf { it.first }
    val maxX = pointsWithAnElf.maxOf { it.second }
    println((maxY - minY + 1) * (maxX - minX + 1) - pointsWithAnElf.size)
}
