package be.tabs_spaces.advent2021.days

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class Day03Test {
    private val day03 = Day03()

    @Test
    fun partOne() {
        Assertions.assertThat(day03.partOne()).isEqualTo(198)
    }

    @Test
    fun partTwo() {
        Assertions.assertThat(day03.partTwo()).isEqualTo(230)
    }
}