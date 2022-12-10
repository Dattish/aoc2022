
import java.io.File

fun main() {
    val lines = File("10.txt").readLines()
    var x = 1L
    var cycle = 1
    var r = 0

    val crt = StringBuilder()
    fun draw(c: Int) {
        if ((x..x+2).contains(c-r)) {
            crt.append('#')
        } else {
            crt.append('.')
        }
        if (c % 40 == 0) {
            crt.append('\n')
            r += 40
        }
    }
    for (line in lines) {
        val s = line.split(" ")
        when (s[0]) {
            "noop" -> {
                draw(cycle)
                cycle++
            }
            "addx" -> {
                draw(cycle)
                cycle++
                draw(cycle)
                cycle++
                x += s[1].toLong()
            }
        }
    }
    println(crt.toString())
}
