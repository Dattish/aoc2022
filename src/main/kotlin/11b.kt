import java.io.File

fun day11b() {
    val lines = File("11.txt").readLines()
    val old = "old "

    data class Test(val divBy: Int, val onTrue: Int, val onFalse: Int)
    data class Operation(val mult: Boolean, val value: Int)
    data class Monkey(val items: MutableList<Long>, val operation: Operation, val test: Test, var inspections: Long = 0)

    val monkeys = mutableListOf<Monkey>()

    var index = 1
    while (index < lines.size) {
        val itemsLine = lines[index]
        val operationLine = lines[index + 1]
        val testLine = lines[index + 2]
        val onTrue = lines[index + 3].last().digitToInt()
        val onFalse = lines[index + 4].last().digitToInt()

        val opSplit = operationLine.substring(operationLine.indexOf(old) + 4)
            .trim()
            .split(' ')
        val operator = opSplit[0].trim() == "*"
        val opValue = if (opSplit[1] == "old") -1 else opSplit[1].toInt()
        monkeys.add(Monkey(
            itemsLine.substring(itemsLine.indexOf(':') + 1)
                .split(',')
                .map { it.trimStart().toLong() }
                .toMutableList(),
            Operation(operator, opValue),
            Test(testLine.split(' ').last().toInt(), onTrue, onFalse)
        ))
        index += 7

    }

    var round = 1
    val mod = monkeys
        .map { it.test.divBy }
        .fold(1) { acc, i -> acc * i }
    while (round <= 10000) {
        for (monkey in monkeys) {
            while (monkey.items.isNotEmpty()) {
                monkey.inspections++
                var worry = monkey.items.removeFirst()
                val opVal = if (monkey.operation.value == -1) worry else monkey.operation.value
                worry = if (monkey.operation.mult) {
                    worry * opVal.toLong()
                } else {
                    worry + opVal.toLong()
                }
                worry %= mod
                val throwTo = if (worry % monkey.test.divBy == 0L) {
                    monkey.test.onTrue
                } else {
                    monkey.test.onFalse
                }
                monkeys[throwTo].items.add(worry)
            }
        }
        round++
    }

    monkeys.sortBy { it.inspections }
    println(monkeys.removeLast().inspections * monkeys.removeLast().inspections)
}
