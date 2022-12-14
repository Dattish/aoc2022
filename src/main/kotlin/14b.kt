import java.io.File
import kotlin.math.max

fun main() {
    val lines = File("14.txt").readLines()

    val grid = Array(1000) { Array(1000) { false } }

    var result = 0
    var maxMaxY = 0

    fun drawPath(coordinates: List<List<Int>>): Int {
        for (i in 1 until coordinates.size) {
            val prevSplit = coordinates[i - 1]
            val currentSplit = coordinates[i]
            val x1 = prevSplit[0]
            val y1 = prevSplit[1]
            val x2 = currentSplit[0]
            val y2 = currentSplit[1]

            var minX: Int
            var maxX: Int
            var minY: Int
            var maxY: Int
            if (x1 > x2) {
                minX = x2
                maxX = x1
            } else {
                minX = x1
                maxX = x2
            }
            if (y1 > y2) {
                minY = y2
                maxY = y1
            } else {
                minY = y1
                maxY = y2
            }
            for (x in minX..maxX) {
                for (y in minY..maxY) {
                    grid[x][y] = true
                }
            }
            maxMaxY = max(maxMaxY, maxY)
        }
        return maxMaxY
    }

    for (line in lines) {
        val coordinates = line.split(" -> ")
        maxMaxY = drawPath(coordinates.map { it.split(',').map { it.toInt() } })
    }
    for (i in grid.indices) {
        grid[i][maxMaxY + 2] = true
    }
    var x = 500
    var y = 0
    while (!grid[500][0] && y < maxMaxY + 4) {
        if (!grid[x][y + 1]) {
            y++
        } else if (!grid[x - 1][y + 1]) {
            y++
            x--
        } else if (!grid[x + 1][y + 1]) {
            y++
            x++
        } else {
            result++
            grid[x][y] = true
            x = 500
            y = 0
        }
    }
    println(result)
}
