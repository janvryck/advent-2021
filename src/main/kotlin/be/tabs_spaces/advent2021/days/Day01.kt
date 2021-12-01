package be.tabs_spaces.advent2021.days

class Day01: Day(1) {
    override fun partOne(): Any {
        return inputList
            .map { it.toInt() }
            .zipWithNext()
            .count { p -> p.second > p.first}
    }

    override fun partTwo(): Any {
        return inputList
            .map { it.toInt() }
            .windowed(3)
            .map { it.sum() }
            .zipWithNext()
            .count { p -> p.second > p.first}
    }
}