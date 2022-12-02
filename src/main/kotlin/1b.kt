import java.nio.file.Files
import java.nio.file.Path
import java.util.*

fun main() {
    val lines = Files.readAllLines(Path.of("1.txt"))
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
    println(
        elves.asSequence()
            .take(3)
            .sum()
    )
}