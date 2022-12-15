import java.io.File

fun day8b() {
    val grid = File("8.txt").readLines()
    val h = grid.size
    val w = grid[0].length
    val scoreGrid = Array(h) { Array(w) { 0 } }

    for (i in 1 until h - 1) {
        for (j in 1 until w - 1) {
            val value = grid[i][j].digitToInt()
            var scoreLeft = j
            var scoreRight = w-(j+1)
            var scoreUp = i
            var scoreDown = h-(i+1)
            for (c in j-1 downTo  0) { // check left
                if (grid[i][c].digitToInt() >= value) {
                    scoreLeft = j - c
                    break
                }
            }
            for (c in j+1 until w) { // check right
                if (grid[i][c].digitToInt() >= value) {
                    scoreRight = c - j
                    break
                }
            }
            for (r in i-1 downTo  0) { // check up
                if (grid[r][j].digitToInt() >= value) {
                    scoreUp = i - r
                    break
                }
            }
            for (r in i+1 until h) { // check down
                if (grid[r][j].digitToInt() >= value) {
                    scoreDown = r - i
                    break
                }
            }
            scoreGrid[i][j] = scoreLeft * scoreRight * scoreUp * scoreDown
        }
    }
    println(scoreGrid.maxBy { it.max() }.max())
}
