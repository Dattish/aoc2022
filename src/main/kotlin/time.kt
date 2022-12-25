import kotlin.system.measureTimeMillis
import kotlin.time.DurationUnit
import kotlin.time.toDuration

fun main() {
    val millis = measureTimeMillis {
        day25()
    }
    println("time: ${millis.toDuration(DurationUnit.MILLISECONDS)}")
}