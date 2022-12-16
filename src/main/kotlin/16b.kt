import java.io.File
import java.util.*
import kotlin.math.max
import kotlin.math.min

fun day16b() {
    val lines = File("16.txt").readLines()

    data class Room(val name: String, val flowRate: Int, val adjacent: List<String>)
    data class ValveOpener(val mins: Int, val room: Room)

    data class State(
        val moving: ValveOpener,
        val waiting: ValveOpener,
        val opened: Set<String>,
        val flow: Int
    )

    val rooms = lines.map { it.split(' ') }.associate {
        val name = it[1]
        val flow = it[4].split('=')[1].split(';')[0].toInt()
        val adjacent = it.drop(9).map { inner -> inner.split(',')[0] }
        name to Room(name, flow, adjacent)
    }

    fun getShortestDistances(current: Room, distance: Int, visited: Set<Room>): Map<String, Int> {
        val distances = HashMap<String, Int>()
        current.adjacent.asSequence()
            .mapNotNull { rooms[it] }
            .filter { it !in visited }
            .forEach {
                if (it.flowRate > 0) {
                    distances[it.name] = distance + 1
                }
                getShortestDistances(it, distance + 1, visited + setOf(current))
                    .forEach { (name, d) -> distances[name] = min(d, distances[name] ?: Int.MAX_VALUE) }
            }
        return distances
    }

    val shortestDistances = rooms.values.associate { it.name to getShortestDistances(it, 0, setOf()) }

    val startingPos = rooms["AA"]!!
    val startTime = 26

    val pq = PriorityQueue(Comparator.comparing(State::flow).reversed())
    pq.add(State(ValveOpener(startTime, startingPos), ValveOpener(startTime, startingPos), sortedSetOf(), 0))
    val visited = mutableMapOf<Set<String>, Int>()

    var best = 0
    while (pq.isNotEmpty()) {
        val (moving, waiting, opened, totalPressure) = pq.poll()
        best = max(best, totalPressure)
        if ((visited[opened] ?: Int.MIN_VALUE) >= totalPressure) {
            continue
        }
        visited[opened] = totalPressure

        pq.addAll(shortestDistances[moving.room.name]!!.mapNotNull { (neighbor, distance) ->
            val newTime = moving.mins - distance - 1
            if (newTime <= 0 || neighbor in opened) {
                return@mapNotNull null
            }
            State(
                waiting,
                ValveOpener(newTime, rooms[neighbor]!!),
                opened + setOf(neighbor),
                totalPressure + rooms[neighbor]!!.flowRate * newTime
            )
        })
    }
    println(best)
}
