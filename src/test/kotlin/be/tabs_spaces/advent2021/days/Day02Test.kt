package be.tabs_spaces.advent2021.days

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class Day02Test {
    private val day02 = Day02()

    @Test
    fun partOne() {
        Assertions.assertThat(day02.partOne()).isEqualTo(150)
    }

    @Test
    fun partTwo() {
        Assertions.assertThat(day02.partTwo()).isEqualTo(900)
    }

}