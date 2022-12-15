
import java.io.File
import kotlin.math.absoluteValue
import kotlin.math.sign

fun day9b() {
    val lines = File("9.txt").readLines()

    data class Coords(var x: Int, var y: Int)


    fun shouldMove(hPos: Coords, tPos: Coords): Boolean {
        val yDiff = (hPos.y - tPos.y).absoluteValue
        val xDiff = (hPos.x - tPos.x).absoluteValue
        return yDiff > 1 || xDiff > 1
    }

    fun getMoves(hPos: Coords, tPos: Coords): Coords {
        return Coords((hPos.x-tPos.x).sign, (hPos.y-tPos.y).sign)
    }

    val positions = Array(10) { Coords(0, 0) }
    val allTPos = mutableSetOf(positions.last().copy())

    for (line in lines) {
        val m = line.split(" ")
        val direction = m[0]
        for (i in 0 until m[1].toInt()) {
            when (direction) {
                "R" -> positions[0].x++
                "L" -> positions[0].x--
                "U" -> positions[0].y++
                "D" -> positions[0].y--
                else -> throw RuntimeException(direction)
            }
            for (j in 1 until positions.size) {
                val hPos = positions[j - 1]
                val tPos = positions[j]
                if (shouldMove(hPos, tPos)) {
                    val move = getMoves(hPos, tPos)
                    tPos.x += move.x
                    tPos.y += move.y
                    if (j == positions.size - 1) {
                        allTPos.add(tPos.copy())
                    }
                }
            }
        }
    }
    println(allTPos.size)
}
