package be.tabs_spaces.advent2021.days

class Day01: Day(1) {
    override fun partOne(): Any {
        return inputList
            .map { it.toInt() }
            .zipWithNext()
            .count { pair -> pair.second > pair.first}
    }

    override fun partTwo(): Any {
        return inputList
            .map { it.toInt() }
            .windowed(4)
            .count { window -> window.last() > window.first()}
    }
}