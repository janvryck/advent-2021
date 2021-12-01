package be.tabs_spaces.advent2021.days

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day01Test {

    val day01 = Day01()

    @Test
    fun partOne() {
        assertThat(day01.partOne()).isEqualTo(7)
    }

    @Test
    fun partTwo() {
        assertThat(day01.partTwo()).isEqualTo(5)
    }
}