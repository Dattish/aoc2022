import java.nio.file.Files
import java.nio.file.Path
import java.util.PriorityQueue

fun main() {
    val lines = Files.readAllLines(Path.of("6.txt"))
    val cap = 4
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


