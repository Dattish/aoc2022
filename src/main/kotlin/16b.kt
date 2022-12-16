
import java.io.File
import java.util.*
import kotlin.math.max

fun day16b() {
    val lines = File("16.txt").readLines()

    data class Room(val name: String, val flow: Int, val adjacent: List<String>)

    val rooms = mutableMapOf<String, Room>()

    for (line in lines) {
        val s = line.split(' ')
        val name = s[1]
        val flow = s[4].split('=')[1].split(';')[0].toInt()
        val adjacent = s.slice(9 until s.size).map { it.split(',')[0] }
        rooms[name] = Room(name, flow, adjacent)
    }

    data class CacheKey(val room: String, val opened: Set<String>, val mins: Int)

    fun peek(room: Room, opened: SortedSet<String>, mins: Int, elephant: Boolean, cache: HashMap<CacheKey, Int>): Int {
        val cacheKey = CacheKey(room.name, opened, mins)
        val cachedValue = cache[cacheKey]
        if (cachedValue != null) {
            return cachedValue
        }

        if (mins <= 0) {
            if (elephant) {
                return peek(rooms["AA"]!!, opened, 26, false, HashMap())
            }
            cache[cacheKey] = 0
            return 0
        }

        var best = 0
        val v = (mins - 1) * room.flow
        val o = TreeSet(opened)
        o.add(room.name)
        for (adj in room.adjacent) {
            if (room.name !in opened && room.flow > 0) {
                best = max(best, v + peek(rooms[adj]!!, o, mins - 2, elephant, cache))
            }
            best = max(best, peek(rooms[adj]!!, opened, mins - 1, elephant, cache))
        }
        cache[cacheKey] = best
        return best
    }

    println(peek(rooms["AA"]!!, TreeSet(), 26, true, HashMap()))
}
