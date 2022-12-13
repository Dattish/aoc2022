import java.io.File

fun main() {
    abstract class File {
        abstract fun size(): Long
    }

    data class Directory(val parent: String, val name: String, val subFiles: MutableSet<File>) : File() {
        override fun size(): Long = subFiles.sumOf { it.size() }
    }

    data class ActualFile(val name: String, val size: Long) : File() {
        override fun size(): Long = size
    }

    val lines = File("7.txt").readLines()
    val dirs = mutableMapOf<String, Directory>()
    val root = Directory("", "", mutableSetOf())
    var currentDir = root
    dirs[root.name] = root

    for (line in lines) {
        val args = line.split(' ')
        if (args[0] == "$") {
            when (args[1]) {
                "cd" -> currentDir = when (args[2]) {
                    "/" -> root
                    ".." -> dirs[currentDir.parent]!!
                    else -> dirs[currentDir.name + '/' + args[2]]!!
                }
            }
        } else if (args[0][0].isDigit()) {
            currentDir.subFiles.add(ActualFile(args[1], args[0].toLong()))
        } else if (args[0] == "dir") {
            val parent = currentDir.name
            val dir = Directory(parent, parent + '/' + args[1], mutableSetOf())
            dirs[dir.name] = dir
            currentDir.subFiles.add(dir)
        }
    }
    var sum = 0L
    for (dir in dirs.values) {
        val size = dir.size()
        if (size < 100000) {
            sum += size
        }
    }
    println(sum)
}
