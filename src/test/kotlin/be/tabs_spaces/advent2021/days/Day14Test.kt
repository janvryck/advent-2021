package be.tabs_spaces.advent2021.days

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day14Test {
    private val day14 = Day14()

    @Test
    fun partOne() {
        assertThat(day14.partOne()).isEqualTo(1588)
    }

    @Test
    fun partTwo() {
        assertThat(day14.partTwo()).isEqualTo(2188189693529)
    }
}