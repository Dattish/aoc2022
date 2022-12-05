import java.nio.file.Files
import java.nio.file.Path

fun main() {
    val lines = Files.readAllLines(Path.of("5.txt"))
    val boxes = mutableListOf<MutableList<Char>>()
    val moveRegex = Regex("move ([0-9]+) from ([0-9]+) to ([0-9]+)")
    lines.forEachIndexed { row, line ->
        if (line != "") {
            val isMove = line[0].isLetter()
            if (!isMove) {
                var expectedBoxes = 0
                var i = 0
                while (i < line.length) {
                    val c1 = line[0 + i]
                    val c2 = line[1 + i]
                    if (c1 == '[') {
                        while ((boxes.size - 1) < expectedBoxes) {
                            boxes.add(mutableListOf())
                        }
                        val current = boxes.elementAtOrElse(expectedBoxes) { mutableListOf() }
                        while ((current.size - 1) < row - 1) {
                            current.add(' ')
                        }
                        current.add(row, c2)
                        boxes[expectedBoxes] = current
                    }
                    expectedBoxes++
                    i += 4
                }
            } else {
                val matchResult = moveRegex.find(line)!!.groupValues
                val num = matchResult[1].toInt()
                val from = matchResult[2].toInt()
                val to = matchResult[3].toInt()
                val takeFrom = boxes[from - 1]
                val giveTo = boxes[to - 1]
                var i = 0
                var taken = 0
                while (taken < num && i < takeFrom.size) {
                    val c = takeFrom.set(i, ' ')
                    if (!c.isWhitespace()) {
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
                    i++
                }
            }
        }
    }
    for (b in boxes.reversed()) {
        println(b)
    }
}


