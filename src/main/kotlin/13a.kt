import Day13A.*
import java.io.File

class Day13A {
    abstract class ParsedValue : Comparable<ParsedValue>

    data class IntValue(val value: Int) : ParsedValue() {
        override fun compareTo(other: ParsedValue): Int {
            return when (other) {
                is IntValue -> value.compareTo(other.value)
                else -> ListValue(this).compareTo(other)
            }
        }
    }

    data class ListValue(val value: List<ParsedValue>) : ParsedValue() {
        constructor(value: ParsedValue) : this(listOf(value))

        override fun compareTo(other: ParsedValue): Int {
            return when (other) {
                is IntValue -> compareTo(ListValue(other))
                is ListValue -> {
                    var i = 0
                    while (i < value.size && i < other.value.size) {
                        val result = value[i].compareTo(other.value[i])
                        if (result != 0) {
                            return result
                        }
                        i++
                    }
                    return value.size - other.value.size
                }

                else -> throw RuntimeException("Unknown value type ${other.javaClass}")
            }
        }
    }
}

fun day13a() {
    val lines = File("13.txt").readLines()

    fun parse(line: String, index: Int): Pair<ParsedValue, Int> {
        if (line[index].isDigit()) {
            val stringBuilder = StringBuilder()
            var currentIndex = index
            while (line[currentIndex].isDigit()) {
                stringBuilder.append(line[currentIndex])
                currentIndex++
            }
            return IntValue(stringBuilder.toString().toInt()) to currentIndex
        }

        var currentIndex = index
        var c: Char
        val list = mutableListOf<ParsedValue>()
        while (currentIndex < line.length - 1) {
            c = line[currentIndex]
            when (c) {
                ']' -> return ListValue(list) to currentIndex + 1
                ',' -> currentIndex++
                else -> {
                    val (value, nextIndex) = if (line[currentIndex].isDigit()) {
                        parse(line, currentIndex)
                    } else {
                        parse(line, currentIndex + 1)
                    }
                    list.add(value)
                    currentIndex = nextIndex
                }
            }
        }
        return ListValue(list) to line.length
    }

    var ordered = 0
    var i = 0
    var pairIndex = 1
    while (i < lines.size) {
        val left = parse(lines[i], 0).first
        val right = parse(lines[i + 1], 0).first

        if (left < right) {
            ordered += pairIndex
        }

        i += 3
        pairIndex++
    }

    println(ordered)
}