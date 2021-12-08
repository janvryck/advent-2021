package be.tabs_spaces.advent2021.days

import be.tabs_spaces.advent2021.days.Day08.SevenSegmentNumber.*

class Day08 : Day(8) {

    companion object {
        val uniqueSegments = listOf(ONE, FOUR, SEVEN, EIGHT)
        val uniqueSegmentLengths = uniqueSegments.map { it.segments }
    }

    override fun partOne() = inputList
        .map { it.split(" | ")[1] }
        .flatMap { it.split(" ") }.map { it.length }
        .count { it in uniqueSegmentLengths }

    override fun partTwo() = inputList
        .map {
            val (patterns, displays) = it.split(" | ")
            SignalPatterns(patterns.split(" ")) to ScrambledDisplays(displays.split(" "))
        }.sumOf {
            it.first.deduceMappings().valueFor(it.second)
        }

    enum class SevenSegmentNumber(val segments: Int, val stringRepresentation: String) {
        ZERO(6, "0"),
        ONE(2, "1"),
        TWO(5, "2"),
        THREE(5, "3"),
        FOUR(4, "4"),
        FIVE(5, "5"),
        SIX(6, "6"),
        SEVEN(3, "7"),
        EIGHT(7, "8"),
        NINE(6, "9")
    }

    data class SignalPatterns(val patterns: List<String>) {
        private val frequencies = patterns.flatMap { it.toList() }
            .groupingBy { it }
            .eachCount()
            .toList()
            .sortedBy { it.second }
        private val deducedMappings: MutableMap<String, SevenSegmentNumber> = mutableMapOf()

        fun deduceMappings(): DeducedMappings {
            deduceMappingsForUniqueLengths()
            deduceTwo()
            deduceThree()
            deduceFive()
            deduceZero()
            deduceNine()
            deduceSix()
            return deducedMappings.toMap()
        }

        private fun deduceMappingsForUniqueLengths() = uniqueSegments.forEach { matchPatternByLength(it) }

        private fun deduceTwo() = unmappedPatterns().first { !it.contains(frequencies.last().first) }.apply { deducedMappings[this] = TWO }

        private fun deduceThree() = unmappedPatterns().first { it.length == 5 && it.hasAllCharsOf(SEVEN) }.apply { deducedMappings[this] = THREE }

        private fun deduceFive() = matchPatternByLength(FIVE)

        private fun deduceZero() = unmappedPatterns().first { !it.hasAllCharsOf(FIVE) }.apply { deducedMappings[this] = ZERO }

        private fun deduceNine() = unmappedPatterns().first { it.hasAllCharsOf(SEVEN) }.apply { deducedMappings[this] = NINE }

        private fun deduceSix() = unmappedPatterns().first().apply { deducedMappings[this] = SIX }

        private fun unmappedPatterns() = patterns.filterNot { deducedMappings.containsKey(it) }

        private fun matchPatternByLength(number: SevenSegmentNumber) = unmappedPatterns()
            .first { it.length == number.segments }
            .apply { deducedMappings[this] = number }

        private fun String.hasAllCharsOf(number: SevenSegmentNumber) = deducedMappings.entries.first { it.value == number }.key.all { c -> c in this }
    }

    data class ScrambledDisplays(val displays: List<String>)
}

typealias DeducedMappings = Map<String, Day08.SevenSegmentNumber>

fun DeducedMappings.valueFor(displays: Day08.ScrambledDisplays): Int = displays.displays.joinToString("") { display ->
    this.entries.first { display.length == it.key.length && it.key.all { c -> c in display } }.value.stringRepresentation
}.toInt()