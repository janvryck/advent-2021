package be.tabs_spaces.advent2021.days

class Day06 : Day(6) {

    companion object {
        private const val HAS_SPAWNED = 6
    }

    private val numberOfFishByAge = groupFishByAge()

    override fun partOne() = iterateSpawnCycle(numberOfFishByAge, 80).values.sum()

    override fun partTwo() = iterateSpawnCycle(numberOfFishByAge, 256).values.sum()

    private fun groupFishByAge(): Map<Int, Long> {
        val fishAtStart = inputString.split(",").map { it.toInt() }
        return (0..8).associateWith { day -> fishAtStart.count { it == day }.toLong() }
    }

    private fun iterateSpawnCycle(numberOfFishByAge: Map<Int, Long>, iterations: Int): Map<Int, Long> {
        return if (iterations > 1)
            iterateSpawnCycle(cycle(numberOfFishByAge), iterations.dec())
        else
            cycle(numberOfFishByAge)
    }

    private fun cycle(numberOfFishByAge: Map<Int, Long>): Map<Int, Long> {
        val spawned = numberOfFishByAge[0] ?: 0
        val agedFish = numberOfFishByAge
            .map { entry -> this.progressAge(entry, spawned) }
            .filterNot { it.first < 0 }
            .toMutableList()
        agedFish.add(8 to spawned)
        return agedFish.toMap()
    }

    private fun progressAge(entry: Map.Entry<Int, Long>, spawned: Long): Pair<Int, Long> {
        val age = entry.key.dec()
        val amount = when (age) {
            HAS_SPAWNED -> entry.value + spawned
            else -> entry.value
        }
        return age to amount
    }
}