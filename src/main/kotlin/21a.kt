import java.io.File

fun day21a() {
    val lines = File("21.txt").readLines()

    val monkeys = lines.map { it.split(':') }
        .associate { it[0] to it[1].trim() }
    val evaluated = mutableMapOf<String, Long>()

    fun eval(monkey: String, expr: String) {
        if (evaluated.containsKey(monkey)) {
            return
        } else if (expr[0].isDigit()) {
            evaluated[monkey] = expr.toLong()
        } else {
            val (lhs, op, rhs) = expr.split(' ')
            eval(lhs, monkeys[lhs]!!)
            eval(rhs, monkeys[rhs]!!)
            val lhsValue = evaluated[lhs]!!
            val rhsValue = evaluated[rhs]!!
            evaluated[monkey] = when (op) {
                "+" -> lhsValue + rhsValue
                "-" -> lhsValue - rhsValue
                "*" -> lhsValue * rhsValue
                "/" -> lhsValue / rhsValue
                else -> throw RuntimeException("unknown operator $op")
            }
        }
    }

    for ((monkey, expr) in monkeys) {
        eval(monkey, expr)
    }

    println(evaluated["root"]!!)
}
