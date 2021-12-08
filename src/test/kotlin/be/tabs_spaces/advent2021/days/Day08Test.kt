package be.tabs_spaces.advent2021.days

import be.tabs_spaces.advent2021.days.Day08.SevenSegmentNumber.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class Day08Test {
    private val day08 = Day08()

    @Test
    fun partOne() {
        assertThat(day08.partOne()).isEqualTo(26)
    }

    @Test
    fun partTwo() {
        assertThat(day08.partTwo()).isEqualTo(61229)
    }

    @Nested
    inner class SignalPatternsTest {
        @Test
        fun `can map signals to 7-segment numbers`() {
            val signalPatterns = Day08.SignalPatterns(listOf("acedgfb", "cdfbe", "gcdfa", "fbcad", "dab", "cefabd", "cdfgeb", "eafb", "cagedb", "ab"))

            val actual = signalPatterns.deduceMappings()

            assertThat(actual).containsExactlyInAnyOrderEntriesOf(
                mapOf(
                    "acedgfb" to EIGHT,
                    "cdfbe" to FIVE,
                    "gcdfa" to TWO,
                    "fbcad" to THREE,
                    "dab" to SEVEN,
                    "cefabd" to NINE,
                    "cdfgeb" to SIX,
                    "eafb" to FOUR,
                    "cagedb" to ZERO,
                    "ab" to ONE,
                )
            )
        }

        @Test
        fun `can translate signal patterns to display value`() {
            val signalPatterns = Day08.SignalPatterns(listOf("acedgfb", "cdfbe", "gcdfa", "fbcad", "dab", "cefabd", "cdfgeb", "eafb", "cagedb", "ab"))

            val actual = signalPatterns.deduceMappings().valueFor(Day08.ScrambledDisplays(listOf("cdfeb", "fcadb", "cdfeb", "cdbaf")))

            assertThat(actual).isEqualTo(5353)
        }
    }
}