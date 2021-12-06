package be.tabs_spaces.advent2021.days

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day06Test {
    private val day06 = Day06()

    @Test
    fun partOne() {
        assertThat(day06.partOne()).isEqualTo(5934)
    }

    @Test
    fun partTwo() {
        assertThat(day06.partTwo()).isEqualTo(26984457539)
    }
}