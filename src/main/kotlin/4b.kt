import java.nio.file.Files
import java.nio.file.Path

fun main() {
    val lines = Files.readAllLines(Path.of("4.txt"))
    var res = 0
    lines.forEach {
        val elves = it.split(",")
        val range1 = elves[0].split("-")
        val range2 = elves[1].split("-")
        if (range1[1].toLong() > range2[1].toLong()) {
            if (range1[0].toLong() <= range2[1].toLong()) {
                res++
            }
        }
        else if (range2[1].toLong() > range1[1].toLong()) {
            if (range2[0].toLong() <= range1[1].toLong()) {
                res++
            }
        } else {
            res++
        }
    }
    println(res)
}


