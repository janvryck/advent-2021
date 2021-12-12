package be.tabs_spaces.advent2021.days

class Day12 : Day(12) {
    private val caveSystem = CaveSystem(inputList)

    override fun partOne() = caveSystem.findExitFromStart().size

    override fun partTwo() = caveSystem.findExitFromStart(maxDuplicateSmallCaves = 1).size

    class CaveSystem(rawInput: List<String>) {
        private val connections = mapTwoWayConnections(rawInput)

        private fun mapTwoWayConnections(rawInput: List<String>) = rawInput
            .map { it.split("-") }
            .flatMap { (from, to) -> listOf(from to to, to to from) }
            .filterNot { it.second.isStart() }
            .groupBy({ it.first }, { it.second })

        fun findExitFromStart(maxDuplicateSmallCaves: Int = 0) = appendPaths(mutableListOf("start"), maxDuplicateSmallCaves)

        private fun appendPaths(
            currentPath: MutableList<String>,
            maxDuplicateSmallCaves: Int
        ) = connections[currentPath.last()]!!
            .map { currentPath + it }
            .flatMap { appendNextPaths(it, maxDuplicateSmallCaves) }

        private fun appendNextPaths(
            path: List<String>,
            maxDuplicateSmallCaves: Int
        ): List<List<String>> = when {
            path.last().isEnd() -> listOf(path)
            hasTooManyDuplicateVisit(path, maxDuplicateSmallCaves) -> emptyList()
            else -> appendPaths(path.toMutableList(), maxDuplicateSmallCaves)
        }

        private fun hasTooManyDuplicateVisit(it: List<String>, maxDuplicateVisits: Int) = it
            .filter { it.isLowercase() }
            .groupingBy { it }
            .eachCount()
            .values
            .let { occurrences -> occurrences.count { it > 1 } > maxDuplicateVisits || occurrences.any { it > 2 } }

        private fun String.isStart() = this == "start"
        private fun String.isEnd() = this == "end"
        private fun String.isLowercase() = this.lowercase() == this
    }

}