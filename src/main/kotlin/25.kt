import java.io.File

fun day25() {
    var decimalSum = File("25.txt").readLines()
        .sumOf { snafuNumber ->
            snafuNumber.map {
                when (it) {
                    '-' -> -1
                    '=' -> -2
                    else -> it.digitToInt()
                }
            }.fold(0L) { acc, next ->
                acc * 5 + next
            }
        }

    val snafuSum = StringBuilder()
    while (decimalSum > 0L) {
        val n = (decimalSum).mod(5)
        snafuSum.append("012=-"[n])
        decimalSum -= if (n > 2) n - 5 else n
        decimalSum /= 5
    }
    println(snafuSum.reverse())
}