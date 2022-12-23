import java.io.File

fun day23b() {
    val pointsWithAnElf = File("23.txt").readLines()
        .flatMapIndexed { y, row -> row.mapIndexedNotNull { x, tile -> if (tile == '#') Pair(y, x) else null } }
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

    val directions = mutableListOf(
        Direction(-1 to -1, -1 to 0, -1 to 1),
        Direction(1 to 1, 1 to 0, 1 to -1),
        Direction(1 to -1, 0 to -1, -1 to -1),
        Direction(-1 to 1, 0 to 1, 1 to 1),
    )

    operator fun MutableSet<Pair<Int, Int>>.contains(movements: Movements): Boolean {
        return contains(movements.left) || contains(movements.center) || contains(movements.right)
    }

    val proposals = mutableMapOf<Pair<Int, Int>, MutableList<Pair<Int, Int>>>()
    var rounds = 0
    do {
        proposals.clear()
        for ((y, x) in pointsWithAnElf) {
            val movements = directions.map { it.toMovements(y, x) }
            if (movements.any { it in pointsWithAnElf }) {
                for (movement in movements.filter { it !in pointsWithAnElf }) {
                    val proposal = proposals[movement.center] ?: mutableListOf()
                    proposal.add(y to x)
                    proposals[movement.center] = proposal
                    break
                }
            }
        }

        proposals.filter { it.value.size == 1 }.forEach { (point, candidates) ->
            pointsWithAnElf.remove(candidates.first())
            pointsWithAnElf.add(point)
        }
        directions.add(directions.removeFirst())
        rounds++
    } while (proposals.isNotEmpty())

    println(rounds)
}
