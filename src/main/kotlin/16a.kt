import java.io.File
import java.util.*
import kotlin.math.max

fun day16a() {
    val lines = File("16.txt").readLines()

    data class Room(val name: String, val flow: Int, val adjacent: List<String>)

    val rooms = lines.map { it.split(' ') }.associate {
        val name = it[1]
        val flow = it[4].split('=')[1].split(';')[0].toInt()
        val adjacent = it.drop(9).map { inner -> inner.split(',')[0] }
        name to Room(name, flow, adjacent)
    }

    data class CacheKey(val room: String, val opened: Set<String>, val mins: Int)

    fun peek(room: Room, opened: SortedSet<String>, mins: Int, cache: HashMap<CacheKey, Int>): Int {
        val cacheKey = CacheKey(room.name, opened, mins)
        val cachedValue = cache[cacheKey]
        if (cachedValue != null) {
            return cachedValue
        }

        if (mins <= 0) {
            cache[cacheKey] = 0
            return 0
        }

        var best = 0
        val v = (mins - 1) * room.flow
        val o = TreeSet(opened)
        o.add(room.name)
        for (adj in room.adjacent) {
            if (room.name !in opened && room.flow > 0) {
                best = max(best, v + peek(rooms[adj]!!, o, mins - 2, cache))
            }
            best = max(best, peek(rooms[adj]!!, opened, mins - 1, cache))
        }
        cache[cacheKey] = best
        return best
    }

    println(peek(rooms["AA"]!!, TreeSet(), 30, HashMap()))
}
