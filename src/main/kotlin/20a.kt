import java.io.File

fun day20a() {
    val lines = File("20.txt").readLines()

    val numbers = lines.map { it.toInt() }
    val currentNumbers = numbers.indices.toMutableList()

    for (i in numbers.indices) {
        val index = currentNumbers.indexOf(i)
        currentNumbers.removeAt(index)
        val insertIndex = (index + numbers[i]).mod(currentNumbers.size)
        if (insertIndex == 0) {
            currentNumbers.add(i)
        } else {
            currentNumbers.add(insertIndex, i)
        }
    }

    val zeroIndex = currentNumbers.indexOf(numbers.indexOf(0))
    println((1..3).sumOf { numbers[currentNumbers[(zeroIndex + it * 1000).mod(currentNumbers.size)]] })
}
