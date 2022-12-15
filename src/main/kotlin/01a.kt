import java.io.File
import java.util.*

fun day1a() {
    val lines = File("1.txt").readLines()
    val elves = TreeSet<Long>(Comparator.reverseOrder())

    var current = 0L
    lines.forEach {
        if (it == "") {
            elves.add(current)
            current = 0L
        } else {
            current += it.toLong()
        }
    }
    elves.add(current)
    println(elves.first())
}