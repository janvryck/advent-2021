package be.tabs_spaces.advent2021.days

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test

class Day04Test {
    private val day04 = Day04()

    @Test
    fun partOne() {
        assertThat(day04.partOne()).isEqualTo(4512)
    }

    @Test
    fun partTwo() {
        assertThat(day04.partTwo()).isEqualTo(1924)
    }
}