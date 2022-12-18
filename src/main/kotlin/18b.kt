import java.io.File

fun day18b() {
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

    val lower = points.minOf { minOf(it.x, it.y, it.z) - 1 }
    val upper = points.maxOf { maxOf(it.x, it.y, it.z) + 1 }

    fun inBounds(point: Point): Boolean {
        return arrayOf(point.x, point.y, point.z).all { it in lower..upper }
    }

    val queue = ArrayDeque<Point>()
    queue.add(Point(lower, lower, lower))
    val visited = mutableSetOf<Point>()

    var result = 0
    while (queue.isNotEmpty()) {
        val point = queue.removeFirst()
        if (point in visited) {
            continue
        }
        visited.add(point)
        for (adjacent in getAdjacent(point)) {
            if (adjacent in points) {
                result++
            } else if (inBounds(adjacent)) {
                queue.add(adjacent)
            }
        }
    }
    println(result)
}
