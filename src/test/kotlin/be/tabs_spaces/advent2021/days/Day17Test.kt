package be.tabs_spaces.advent2021.days

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day17Test {
    private val day17 = Day17()

    @Test
    fun partOne() {
        assertThat(day17.partOne()).isEqualTo(45)
    }

    @Test
    fun partTwo() {
        assertThat(day17.partTwo()).isEqualTo(112)
    }
}