
import java.io.File

fun main() {
    val lines = File("10.txt").readLines()
    var x = 1L
    var cycle = 0

    fun getSigStr(i: Int, s: List<String>): Long {
        return i * (x - when (s[0]) {
            "addx" -> s[1].toLong()
            else -> 0
        })
    }
    val nextInteresting = ArrayDeque<Int>()
    nextInteresting.addAll(listOf(20, 60, 100, 140, 180, 220))
    var total = 0L
    for (line in lines) {
        val s = line.split(" ")
        val inst = s[0]
        when (inst) {
            "noop" -> cycle++
            "addx" -> {
                cycle+=2
                x += s[1].toLong()
            }
        }
        if (cycle >= nextInteresting.first()) {
            total += getSigStr(nextInteresting.removeFirst(), s)
        }
        if (nextInteresting.isEmpty()) {
            break
        }
    }
    println(total)
}
