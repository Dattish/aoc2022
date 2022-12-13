import java.io.File

fun main() {
    val grid = File("8.txt").readLines()
    val h = grid.size
    val w = grid[0].length
    var visible = (h * 2) + (w * 2) - 4

    for (i in 1 until h - 1) {
        for (j in 1 until w - 1) {
            val value = grid[i][j].digitToInt()
            var scoreLeft = true
            var scoreRight = true
            var scoreUp = true
            var scoreDown = true
            for (c in j-1 downTo  0) { // check left
                if (grid[i][c].digitToInt() >= value) {
                    scoreLeft = false
                    break
                }
            }
            for (c in j+1 until w) { // check right
                if (grid[i][c].digitToInt() >= value) {
                    scoreRight = false
                    break
                }
            }
            for (r in i-1 downTo  0) { // check up
                if (grid[r][j].digitToInt() >= value) {
                    scoreUp = false
                    break
                }
            }
            for (r in i+1 until h) { // check down
                if (grid[r][j].digitToInt() >= value) {
                    scoreDown = false
                    break
                }
            }
            if (scoreLeft || scoreRight || scoreUp || scoreDown) {
                visible++
            }
        }
    }
    println(visible)
}
