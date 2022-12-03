import java.nio.file.Files
import java.nio.file.Path

fun main() {
    fun getPriority(char: Char): Int =
        if (char.isLowerCase())
                char.code - 96
            else
                char.code - 38

    val lines = Files.readAllLines(Path.of("3.txt"))

    val elves = mutableListOf<MutableList<MutableSet<Int>>>()
    for (c in 0 until lines.size) {
        val group = c/3
        val elf = c - 3 * group
        if (elves.elementAtOrNull(group) == null) {
            elves.add(group, mutableListOf())
        }
        if (elves[group].elementAtOrNull(elf) == null) {
            elves[group].add(mutableSetOf())
        }
        lines[c].forEach { elves[group][elf].add(getPriority(it)) }
    }
    var res = 0L
    elves.forEach {
        val first = it[0]
        val second = it[1]
        res += it[2].filter { i -> first.contains(i) && second.contains(i) }[0]
    }
    println(res)
}


