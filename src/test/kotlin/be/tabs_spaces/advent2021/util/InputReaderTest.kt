package be.tabs_spaces.advent2021.util

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchThrowable
import org.junit.jupiter.api.Test
import java.io.FileNotFoundException

class InputReaderTest {

    @Test
    fun `Read unknown file`() {
        val day = -1

        val thrown = catchThrowable{ InputReader.getInputAsString(day) }

        assertThat(thrown).isInstanceOfSatisfying(FileNotFoundException::class.java) {
            assertThat(it.message).isEqualTo("No inputfile found for day -1")
        }
    }

    @Test
    fun `Read existing file as String`() {
        val day = 0

        val input = InputReader.getInputAsString(day)

        assertThat(input).isEqualTo(
            """
            input
            for
            day-0
        """.trimIndent()
        )
    }

    @Test
    fun `Read existing file as List`() {
        val day = 0

        val input = InputReader.getInputAsList(day)

        assertThat(input).containsExactly("input", "for", "day-0")
    }
}