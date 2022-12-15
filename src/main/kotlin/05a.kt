
import java.io.File

fun day5a() {
    val lines = File("5.txt").readLines()
    val boxes = mutableListOf<ArrayDeque<Char>>()
    val moveRegex = Regex("move ([0-9]+) from ([0-9]+) to ([0-9]+)")
    lines.filter { it != "" }.forEach {
        val isMove = it[0].isLetter()
        if (!isMove) {
            var expectedBoxes = 0
            var i = 0
            while (i < it.length) {
                val c1 = it[0 + i]
                val c2 = it[1 + i]
                if (c1 == '[') {
                    while ((boxes.size - 1) < expectedBoxes) {
                        boxes.add(ArrayDeque())
                    }
                    val current = boxes.elementAtOrElse(expectedBoxes) { ArrayDeque() }
                    current.add(c2)
                    boxes[expectedBoxes] = current
                }
                expectedBoxes++
                i += 4
            }
        } else {
            val matchResult = moveRegex.find(it)!!.groupValues
            val num = matchResult[1].toInt()
            val from = matchResult[2].toInt()
            val to = matchResult[3].toInt()
            val takeFrom = boxes[from - 1]
            val giveTo = boxes[to - 1]
            var taken = 0
            while (taken < num) {
                val c = takeFrom.removeFirst()
                var indexToSet = 0
                while (indexToSet < giveTo.size && giveTo[indexToSet].isWhitespace()) {
                    indexToSet++
                }
                if (indexToSet == 0) {
                    giveTo.add(0, c)
                } else {
                    giveTo[indexToSet - 1] = c
                }
                taken++
            }
        }
    }
    for (b in boxes) {
        print(b.first())
    }
}


