
import java.io.File

fun day3a() {
    fun getPriority(char: Char): Int =
        if (char.isLowerCase())
                char.code - 96
            else
                char.code - 38

    val lines = File("3.txt").readLines()

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


