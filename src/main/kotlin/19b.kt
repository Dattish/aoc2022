import java.io.File
import kotlin.math.max

fun day19b() {
    val lines = File("19.txt").readLines()
    val numbersRegex =
        Regex("Blueprint (\\d+): Each ore robot costs (\\d+) ore. Each clay robot costs (\\d+) ore. Each obsidian robot costs (\\d+) ore and (\\d+) clay. Each geode robot costs (\\d+) ore and (\\d+) obsidian.")

    data class Cost(val ore: Int = 0, val clay: Int = 0, val obsidian: Int = 0)

    data class Blueprint(
        val blueprintId: Int,
        val oreRobotCost: Cost,
        val clayRobotCost: Cost,
        val obsidianRobotCost: Cost,
        val geodeRobotCost: Cost
    )

    data class Robots(val ore: Int = 0, val clay: Int = 0, val obsidian: Int = 0, val geode: Int = 0)
    data class Inventory(val ore: Int = 0, val clay: Int = 0, val obsidian: Int = 0, val geode: Int = 0)
    data class Mined(val ore: Int, val clay: Int, val obsidian: Int, val geode: Int)
    data class State(val minute: Int, val robots: Robots, val inventory: Inventory, val mined: Mined)

    val blueprints = lines.map {
        val matches = numbersRegex.find(it)!!.groupValues
        val blueprintId = matches[1].toInt()
        val oreCost = matches[2].toInt()
        val clayCost = matches[3].toInt()
        val obsidianOreCost = matches[4].toInt()
        val obsidianClayCost = matches[5].toInt()
        val geodeOreCost = matches[6].toInt()
        val geodeObsidianCost = matches[7].toInt()
        Blueprint(
            blueprintId,
            Cost(ore = oreCost),
            Cost(ore = clayCost),
            Cost(ore = obsidianOreCost, clay = obsidianClayCost),
            Cost(ore = geodeOreCost, obsidian = geodeObsidianCost)
        )
    }


    val limit = 10000
    val comparator =
        compareBy<State> { 1000 * it.mined.geode + 100 * it.mined.obsidian + 10 * it.mined.clay + it.mined.ore }.reversed()

    fun mineGeodes(blueprint: Blueprint, initialState: State, maxMinutes: Int): Int {
        var maxMined = 0
        var depth = 0
        var queue = ArrayDeque<State>()
        queue.add(initialState)

        while (queue.isNotEmpty()) {
            val (minute, robots, inventory, mined) = queue.removeFirst()

            if (minute > depth) {
                queue.sortWith(comparator)
                queue = ArrayDeque(queue.take(limit))
                depth = minute
            }

            if (minute == maxMinutes) {
                maxMined = max(maxMined, mined.geode)
                continue
            }

            val newInventory = Inventory(
                inventory.ore + robots.ore,
                inventory.clay + robots.clay,
                inventory.obsidian + robots.obsidian,
                inventory.geode + robots.geode
            )

            val newMined = Mined(
                mined.ore + robots.ore,
                mined.clay + robots.clay,
                mined.obsidian + robots.obsidian,
                mined.geode + robots.geode
            )

            val noBuild = State(minute + 1, robots, newInventory, newMined)
            queue.add(noBuild)

            if (inventory.ore >= blueprint.oreRobotCost.ore) {
                queue.add(
                    noBuild.copy(
                        robots = robots.copy(ore = robots.ore + 1),
                        inventory = newInventory.copy(ore = noBuild.inventory.ore - blueprint.oreRobotCost.ore)
                    )
                )
            }

            if (inventory.ore >= blueprint.clayRobotCost.ore) {
                queue.add(
                    noBuild.copy(
                        robots = robots.copy(clay = robots.clay + 1),
                        inventory = newInventory.copy(ore = noBuild.inventory.ore - blueprint.clayRobotCost.ore)
                    )
                )
            }

            if (inventory.ore >= blueprint.obsidianRobotCost.ore && inventory.clay >= blueprint.obsidianRobotCost.clay) {
                queue.add(
                    noBuild.copy(
                        robots = robots.copy(obsidian = robots.obsidian + 1), inventory = newInventory.copy(
                            ore = noBuild.inventory.ore - blueprint.obsidianRobotCost.ore,
                            clay = noBuild.inventory.clay - blueprint.obsidianRobotCost.clay
                        )
                    )
                )
            }

            if (inventory.ore >= blueprint.geodeRobotCost.ore && inventory.obsidian >= blueprint.geodeRobotCost.obsidian) {
                queue.add(
                    noBuild.copy(
                        robots = robots.copy(geode = robots.geode + 1), inventory = newInventory.copy(
                            ore = noBuild.inventory.ore - blueprint.geodeRobotCost.ore,
                            obsidian = noBuild.inventory.obsidian - blueprint.geodeRobotCost.obsidian
                        )
                    )
                )
            }
        }
        return maxMined
    }

    var result = 1
    for (blueprint in blueprints.take(3)) {
        val mined = mineGeodes(
            blueprint, State(
                0, Robots(1, 0, 0, 0), Inventory(0, 0, 0, 0), Mined(0, 0, 0, 0)
            ), 32
        )
        result *= mined
    }
    println(result)
}
