package be.tabs_spaces.advent2021.days

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day15Test {
    private val day15 = Day15()

    @Test
    fun partOne() {
        assertThat(day15.partOne()).isEqualTo(40)
    }

    @Test
    fun partTwo() {
        assertThat(day15.partTwo()).isEqualTo(315)
    }
}