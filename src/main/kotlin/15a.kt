import java.io.File
import kotlin.math.absoluteValue

fun day15a() {
    val lines = File("15.txt").readLines()
    val regex = Regex("Sensor at x=(-?[0-9]+), y=(-?[0-9]+): closest beacon is at x=(-?[0-9]+), y=(-?[0-9]+)")
    val yToLookAt = 2000000

    data class Sensor(val x: Int, val y: Int, val range: Int)

    fun inRangeOfToLookAt(sensor: Sensor): Boolean {
        val yRange = (sensor.y - sensor.range)..(sensor.y + sensor.range)
        return yRange.contains(yToLookAt)
    }

    val sensors = mutableSetOf<Sensor>()
    val beaconsOnY = mutableSetOf<Int>()
    for (line in lines) {
        val matchResult = regex.find(line)!!.groupValues
        val x = matchResult[1].toInt()
        val y = matchResult[2].toInt()
        val bX = matchResult[3].toInt()
        val bY = matchResult[4].toInt()
        val range = (x - bX).absoluteValue + (y - bY).absoluteValue
        val sensor = Sensor(x, y, range)
        if (inRangeOfToLookAt(sensor)) {
            sensors.add(sensor)
        }
        if (bY == yToLookAt) {
            beaconsOnY.add(bX)
        }
    }
    val xs = mutableSetOf<Int>()
    for (sensor in sensors) {
        val stepsRangeAfterY = sensor.range - (sensor.y - yToLookAt).absoluteValue
        xs.add(sensor.x)
        for (i in -stepsRangeAfterY..stepsRangeAfterY) {
            val xToAdd = sensor.x + i
            if (!beaconsOnY.contains(xToAdd)) {
                xs.add(xToAdd)
            }
        }
    }
    println(xs.size)
}
