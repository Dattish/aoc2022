import java.nio.file.Files
import java.nio.file.Path

fun main() {
    fun getPriority(char: Char): Int =
        if (char.isLowerCase())
                char.code - 96
            else
                char.code - 38

    val lines = Files.readAllLines(Path.of("3.txt"))

    val firstHalf = mutableSetOf<Int>()
    val secondHalf = mutableSetOf<Int>()
    var res = 0L
    lines.forEach {
        if (it != "") {
            val totalLength = it.length
            val halfLength = totalLength / 2
            for (i in 0 until totalLength) {
                val priority = getPriority(it[i])
                if (i < halfLength) {
                    firstHalf.add(priority)
                } else {
                    secondHalf.add(priority)
                }
            }
            firstHalf.retainAll(secondHalf)
            res += firstHalf.sum()
            firstHalf.clear()
            secondHalf.clear()
        }
    }
    println(res)
}


