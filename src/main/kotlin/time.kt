import kotlin.system.measureTimeMillis
import kotlin.time.DurationUnit
import kotlin.time.toDuration

fun main() {
    val millis = measureTimeMillis {
        day23b()
    }
    println("time: ${millis.toDuration(DurationUnit.MILLISECONDS)}")
}