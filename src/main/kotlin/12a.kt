import java.io.File

fun main() {
    val graph = File("12.txt").readLines().map { it.toCharArray() }
    val maxH = graph.size
    val maxW = graph[0].size
    val allowedHeight = 0 until maxH
    val allowedWidth = 0 until maxW

    data class Vertex(val h: Int, val w: Int)

    var start: Vertex? = null
    var end: Vertex? = null
    outer@ for (h in allowedHeight) {
        for (w in allowedWidth) {
            when (graph[h][w]) {
                'S' -> start = Vertex(h, w)
                'E' -> end = Vertex(h, w)
            }
            if (start != null && end != null) {
                break@outer
            }
        }
    }

    requireNotNull(start)
    requireNotNull(end)

    val q = ArrayDeque<Vertex>()
    val distMap = HashMap<Vertex, Int>()

    fun enqueue(h: Int, w: Int, dist: Int) {
        val vertex = Vertex(h, w)
        if (vertex in distMap) {
            return
        }
        distMap[vertex] = dist
        q.add(vertex)
    }

    enqueue(end.h, end.w, 0)

    fun getElevation(c: Char): Int {
        return when (c) {
            'S' -> 0
            'E' -> 25
            else -> c - 'a'
        }
    }

    fun visit(h: Int, w: Int, dist: Int, currentElevation: Int) {
        if (!allowedHeight.contains(h) || !allowedWidth.contains(w)) {
            return
        }
        val targetElevation = getElevation(graph[h][w]) + 1
        if (currentElevation <= targetElevation) {
            enqueue(h, w, dist)
        }
    }

    while (q.isNotEmpty()) {
        val v = q.removeFirst()
        val dist = distMap[v]!!
        val c = graph[v.h][v.w]
        if (c == 'S') {
            println(dist)
            return
        }
        val elevation = getElevation(c)

        val doVisit = { i: Int, j: Int -> visit(i, j, dist + 1, elevation) }
        doVisit(v.h - 1, v.w)
        doVisit(v.h + 1, v.w)
        doVisit(v.h, v.w - 1)
        doVisit(v.h, v.w + 1)
    }
}
