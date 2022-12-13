import java.io.File

fun main() {
    fun calculateWinScore(translatedOpponent: String, translatedYou: String): Int {
        if (translatedOpponent == translatedYou) {
            return 3
        }
        return when (translatedOpponent) {
            "R" -> when (translatedYou) {
                "P" -> 6
                "S" -> 0
                else -> throw RuntimeException(translatedYou)
            }

            "P" -> when (translatedYou) {
                "S" -> 6
                "R" -> 0
                else -> throw RuntimeException(translatedYou)
            }

            "S" -> when (translatedYou) {
                "R" -> 6
                "P" -> 0
                else -> throw RuntimeException(translatedYou)
            }

            else -> throw RuntimeException(translatedOpponent)
        }
    }

    fun translateYours(you: String): String {
        return when (you) {
            "X" -> "R"
            "Y" -> "P"
            "Z" -> "S"
            else -> throw RuntimeException(you)
        }
    }

    fun translateOpponents(opponent: String): String {
        return when (opponent) {
            "A" -> "R"
            "B" -> "P"
            "C" -> "S"
            else -> throw RuntimeException(opponent)
        }
    }

    fun getScore(opponent: String, you: String): Int {
        val translatedOpponent = translateOpponents(opponent)
        val translatedYou = translateYours(you)

        val won = calculateWinScore(translatedOpponent, translatedYou)
        return when (translatedYou) {
            "R" -> 1
            "P" -> 2
            "S" -> 3
            else -> throw RuntimeException(translatedYou)
        } + won
    }

    val lines = File("2.txt").readLines()

    var res = 0L
    lines.forEach {
        if (it != "") {
            val split = it.split(' ')
            res += getScore(split[0], split[1])
        }
    }
    println(res)
}

