package be.tabs_spaces.advent2021.days

class Day00: Day(0) {

    override fun partOne(): Any {
        return inputString.lines().count()
    }

    override fun partTwo(): Any {
        return inputList.count()
    }
}