
import java.io.File

fun main() {
    val lines = File("6.txt").readLines()
    val cap = 14
    val q = ArrayDeque<Int>(cap + 1)
    var count = 0L
    for (it in lines) {
        for (c in it.chars()) {
            var indexOf = q.indexOf(c)
            while (indexOf-- >= 0) {
                q.removeFirst()
            }
            q.addLast(c)
            count++
            if (q.size == cap) {
                println(count)
                return
            }
        }
    }
}


