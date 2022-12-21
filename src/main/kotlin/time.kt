import kotlin.system.measureTimeMillis
import kotlin.time.DurationUnit
import kotlin.time.toDuration

fun main() {
    val millis = measureTimeMillis {
        day21b()
    }
    println("time: ${millis.toDuration(DurationUnit.MILLISECONDS)}")
}