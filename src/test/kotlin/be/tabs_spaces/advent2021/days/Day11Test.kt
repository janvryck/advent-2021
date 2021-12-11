package be.tabs_spaces.advent2021.days

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day11Test {
    private val day11 = Day11()
    private val day11bis = Day11()

    @Test
    fun partOne() {
        assertThat(day11.partOne()).isEqualTo(1656)
        assertThat(day11bis.partOne()).isEqualTo(1656)
    }

    @Test
    fun partTwo() {
        assertThat(day11.partTwo()).isEqualTo(195)
        assertThat(day11bis.partTwo()).isEqualTo(195)
    }
}