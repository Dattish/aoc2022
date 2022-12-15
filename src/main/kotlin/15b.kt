import java.io.File
import kotlin.math.absoluteValue

fun main() {
    val lines = File("15.txt").readLines()
    val regex = Regex("Sensor at x=(-?[0-9]+), y=(-?[0-9]+): closest beacon is at x=(-?[0-9]+), y=(-?[0-9]+)")

    data class Sensor(val x: Long, val y: Long, val range: Long)
    val sensors = mutableListOf<Sensor>()

    val coordRange = 0L..4000000L
    fun inCoordRange(x: Long, y: Long): Boolean {
        return x in coordRange && y in coordRange
    }
    fun notInAnySensorRange(x: Long, y: Long): Boolean {
        return sensors.all { s -> s.range < (s.x - x).absoluteValue + (s.y - y).absoluteValue }
    }

    fun getAnswer(sensor: Sensor): Pair<Long, Long>? {
        var x = sensor.x - sensor.range - 1
        var y = sensor.y

        while (x < sensor.x) {
            if (inCoordRange(x, y) && notInAnySensorRange(x, y)) {
                return x to y
            }
            x++
            y++
        }

        while (y > sensor.y) {
            if (inCoordRange(x, y) && notInAnySensorRange(x, y)) {
                return x to y
            }
            x++
            y--
        }

        while (x > sensor.x) {
            if (inCoordRange(x, y) && notInAnySensorRange(x, y)) {
                return x to y
            }
            x--
            y--
        }

        while (y < sensor.y) {
            if (inCoordRange(x, y) && notInAnySensorRange(x, y)) {
                return x to y
            }
            x--
            y++
        }
        return null
    }

    for (line in lines) {
        val matchResult = regex.find(line)!!.groupValues
        val x = matchResult[1].toLong()
        val y = matchResult[2].toLong()
        val bX = matchResult[3].toLong()
        val bY = matchResult[4].toLong()
        val range = (x - bX).absoluteValue + (y - bY).absoluteValue
        val sensor = Sensor(x, y, range)
        sensors.add(sensor)
    }

    for (sensor in sensors) {
        val answer = getAnswer(sensor)
        if (answer != null) {
            val (x, y) = answer
            println(x * 4000000 + y)
            return
        }
    }
}
