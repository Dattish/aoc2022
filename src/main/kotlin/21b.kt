import java.io.File

fun day21b() {
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
            val rhsExpr = monkeys[rhs]!!
            val lhsExpr = monkeys[lhs]!!
            if (lhs == "humn") {
                eval(rhs, rhsExpr)
                return
            } else if (rhs == "humn") {
                eval(lhs, lhsExpr)
                return
            }
            eval(rhs, rhsExpr)
            eval(lhs, lhsExpr)
            if (!evaluated.containsKey(lhs) || !evaluated.containsKey(rhs)) {
                return
            }
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

    val (rootLhs, _, rootRhs) = monkeys["root"]!!.split(" ")

    eval(rootLhs, monkeys[rootLhs]!!)
    eval(rootRhs, monkeys[rootRhs]!!)

    var (unsolved, remainder) = if (!evaluated.containsKey(rootLhs)) {
        rootLhs to evaluated[rootRhs]!!
    } else {
        rootRhs to evaluated[rootLhs]!!
    }

    while (unsolved != "humn") {
        val (lhs, op, rhs) = monkeys[unsolved]!!.split(' ')

        if (!evaluated.containsKey(lhs)) {
            unsolved = lhs
            val rhsValue = evaluated[rhs]!!
            remainder = when (op) {
                "+" -> remainder - rhsValue
                "-" -> remainder + rhsValue
                "*" -> remainder / rhsValue
                "/" -> remainder * rhsValue
                else -> throw RuntimeException("unknown operator $op")
            }
        } else {
            unsolved = rhs
            val lhsValue = evaluated[lhs]!!
            remainder = when (op) {
                "+" -> remainder - lhsValue
                "-" -> lhsValue - remainder
                "*" -> remainder / lhsValue
                "/" -> lhsValue / remainder
                else -> throw RuntimeException("unknown operator $op")
            }
        }
    }

    println(remainder)
}
