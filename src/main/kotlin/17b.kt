import java.io.File
import kotlin.math.max

fun day17b() {
    val jets = File("17.txt").readText()

    data class Rock(val array: List<List<Boolean>>) {
        val height = array.size
        val width = array[0].size
    }

    val rocks = listOf(
        listOf("####"),
        listOf(".#.", "###", ".#."),
        listOf("..#", "..#", "###"),
        listOf("#", "#", "#", "#"),
        listOf("##", "##")
    ).map { Rock(it.map { string -> string.toList().map { it == '#' } }) }

    val maxWidth = 7

    val grid = mutableListOf(BooleanArray(maxWidth) { true })

    fun willCollide(rock: Rock, height: Int, minWidth: Int): Boolean {
        for (h in rock.array.indices) {
            val row = rock.array[h]
            for (w in row.indices) {
                val occupied = row[w]
                if (!occupied) {
                    continue
                }
                if (grid[height - h][minWidth + w]) {
                    return true
                }
            }
        }
        return false
    }

    fun willCollideHorizontally(rock: Rock, height: Int, minWidth: Int, rockMove: Int): Boolean {
        return willCollide(rock, height, minWidth + rockMove)
    }

    fun willCollideVertically(rock: Rock, height: Int, minWidth: Int): Boolean {
        return willCollide(rock, height - 1, minWidth)
    }

    var jetIndex = 0
    fun moveIntoPlace(rock: Rock, top: Int): Pair<Int, Int> {
        var maxHeight = top + rock.height + 3
        var minWidth = 2

        while (true) {
            val rockMove = if (jets[jetIndex++ % jets.length] == '>') {
                1
            } else {
                -1
            }
            minWidth = when {
                minWidth + rockMove !in 0..maxWidth - rock.width -> minWidth
                willCollideHorizontally(rock, maxHeight, minWidth, rockMove) -> minWidth
                else -> minWidth + rockMove
            }
            if (willCollideVertically(rock, maxHeight, minWidth)) {
                break
            } else {
                maxHeight--
            }
        }

        return maxHeight to minWidth
    }

    fun drawRock(rock: Rock, maxHeight: Int, minWidth: Int) {
        for (h in rock.array.indices) {
            val row = rock.array[h]
            for (w in row.indices) {
                if (row[w]) {
                    grid[maxHeight - h][minWidth + w] = true
                }
            }
        }
    }

    fun fall(rock: Rock, top: Int): Int {
        for (i in 0..(top + rock.height + 3) - grid.lastIndex) {
            grid.add(BooleanArray(maxWidth))
        }

        val (maxHeight, minWidth) = moveIntoPlace(rock, top)
        drawRock(rock, maxHeight, minWidth)

        return max(top, maxHeight)
    }


    val topHistory = mutableListOf(0)
    for (i in 0..5000) {
        topHistory.add(fall(rocks[i % rocks.size], topHistory.last()))
    }

    val deltas = (1..topHistory.lastIndex).map { topHistory[it] - topHistory[it - 1] }

    fun findRepeatingSequence(): Pair<Int, Int> {
        var start = 0
        var sequenceLength: Int? = null
        while (sequenceLength == null) {
            var guess = 1500
            while (start + 2 * guess < deltas.lastIndex) {
                if (deltas.subList(start, start + guess) == deltas.subList(start + guess, start + 2 * guess)) {
                    sequenceLength = guess
                }
                guess++
            }
            start++
        }
        return start to sequenceLength
    }

    val (start, sequenceLength) = findRepeatingSequence()

    val simulations = 1000000000000
    val loops = (simulations - start) / sequenceLength
    val rem = (simulations - start) % sequenceLength
    println(
        deltas.subList(0, start).sum().toLong() +
                loops * deltas.subList(start, start + sequenceLength).sum().toLong() +
                deltas.subList(start, start + rem.toInt()).sum()
    )
}