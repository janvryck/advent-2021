package be.tabs_spaces.advent2021.days

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day05Test {
    private val day05 = Day05()

    @Test
    fun partOne() {
        assertThat(day05.partOne()).isEqualTo(5)
    }

    @Test
    fun partTwo() {
        assertThat(day05.partTwo()).isEqualTo(12)
    }
}