import java.io.File

fun day18a() {
    val lines = File("18.txt").readLines()

    data class Point(val x: Int, val y: Int, val z: Int)

    val points = lines.map { it.split(',').map { it.toInt() } }.map { (x, y, z) -> Point(x, y, z) }.toSet()

    fun getAdjacent(point: Point): Set<Point> {
        return setOf(
            point.copy(x = point.x + 1),
            point.copy(x = point.x - 1),
            point.copy(y = point.y + 1),
            point.copy(y = point.y - 1),
            point.copy(z = point.z + 1),
            point.copy(z = point.z - 1)
        )
    }

    println(points.sumOf { getAdjacent(it).count { adjacent -> adjacent !in points } })
}
