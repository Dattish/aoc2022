import java.nio.file.Files
import java.nio.file.Path

fun main() {
    fun getPriority(char: Char): Int =
        if (char.isLowerCase())
            char.code - 96
        else
            char.code - 38

    val lines = Files.readAllLines(Path.of("3.txt"))

    val firstElf = mutableSetOf<Char>()
    val secondElf = mutableSetOf<Char>()
    var res = 0L
    lines.forEachIndexed { i, line ->
        when (i - 3 * (i / 3)) {
            2 -> {
                for (c in line) {
                    if (firstElf.contains(c) && secondElf.contains(c)) {
                        res += getPriority(c)
                        firstElf.clear()
                        secondElf.clear()
                        break
                    }
                }
            }
            1 -> line.forEach { firstElf.add(it) }
            else -> line.forEach { secondElf.add(it) }
        }
    }
    println(res)
}


