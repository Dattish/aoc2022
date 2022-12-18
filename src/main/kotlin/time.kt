import kotlin.system.measureTimeMillis
import kotlin.time.DurationUnit
import kotlin.time.toDuration

fun main() {
    val millis = measureTimeMillis {
        day18b()
    }
    println("time: ${millis.toDuration(DurationUnit.MILLISECONDS)}")
}