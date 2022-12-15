import java.io.File
import kotlin.math.absoluteValue
import kotlin.math.sign

fun day9a() {
    val lines = File("9.txt").readLines()

    data class Coords(var x: Long, var y: Long)

    fun handleYMove(hPos: Coords, tPos: Coords, followMove: () -> Boolean) {
        val yDiff = (hPos.y - tPos.y).absoluteValue
        if (yDiff > 1 && tPos.x != hPos.x) {
            val xDiff = hPos.x - tPos.x
            when (xDiff.sign) {
                1 -> {
                    tPos.x++
                    followMove()
                }

                -1 -> {
                    tPos.x--
                    followMove()
                }
            }
        }
    }

    fun handleXMove(hPos: Coords, tPos: Coords, followMove: () -> Boolean) {
        val xDiff = (hPos.x - tPos.x).absoluteValue
        if (xDiff > 1 && tPos.y != hPos.y) {
            val yDiff = hPos.y - tPos.y
            when (yDiff.sign) {
                1 -> {
                    tPos.y++
                    followMove()
                }

                -1 -> {
                    tPos.y--
                    followMove()
                }
            }
        }
    }

    val tPos = Coords(0, 0)
    val hPos = Coords(0, 0)
    val allTPos = mutableSetOf(tPos.copy())

    for (line in lines) {
        val m = line.split(" ")
        val direction = m[0]
        var hMoves = m[1].toInt()

        when (direction) {
            "R" -> {
                val followMove = {
                    tPos.x++
                    allTPos.add(tPos.copy())
                }
                while (hMoves > 0) {
                    hMoves--
                    hPos.x++
                    handleXMove(hPos, tPos, followMove)
                    val xDiff = (hPos.x - tPos.x).absoluteValue
                    val yDiff = (hPos.y - tPos.y).absoluteValue
                    if (xDiff > 1 || yDiff > 1) {
                        followMove()
                    }
                }
            }

            "L" -> {
                val followMove = {
                    tPos.x--
                    allTPos.add(tPos.copy())
                }
                while (hMoves > 0) {
                    hMoves--
                    hPos.x--
                    handleXMove(hPos, tPos, followMove)
                    val xDiff = (hPos.x - tPos.x).absoluteValue
                    val yDiff = (hPos.y - tPos.y).absoluteValue
                    if (xDiff > 1 || yDiff > 1) {
                        followMove()
                    }
                }
            }

            "U" -> {
                val followMove = {
                    tPos.y++
                    allTPos.add(tPos.copy())
                }
                while (hMoves > 0) {
                    hMoves--
                    hPos.y++
                    handleYMove(hPos, tPos, followMove)
                    val xDiff = (hPos.x - tPos.x).absoluteValue
                    val yDiff = (hPos.y - tPos.y).absoluteValue
                    if (xDiff > 1 || yDiff > 1) {
                        followMove()
                    }
                }
            }

            else -> {
                val followMove = {
                    tPos.y--
                    allTPos.add(tPos.copy())
                }
                while (hMoves > 0) {
                    hMoves--
                    hPos.y--
                    handleYMove(hPos, tPos, followMove)
                    val xDiff = (hPos.x - tPos.x).absoluteValue
                    val yDiff = (hPos.y - tPos.y).absoluteValue
                    if (xDiff > 1 || yDiff > 1) {
                        followMove()
                    }
                }
            }
        }
    }
    println(allTPos.size)
}
