package be.tabs_spaces.advent2021.days

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class Day09Test {
    private val day09 = Day09()

    @Test
    fun partOne() {
        assertThat(day09.partOne()).isEqualTo(15)
    }

    @Test
    fun partTwo() {
        assertThat(day09.partTwo()).isEqualTo(1134)
    }
}