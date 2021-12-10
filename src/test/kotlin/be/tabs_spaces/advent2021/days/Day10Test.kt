package be.tabs_spaces.advent2021.days

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class Day10Test {
    private val day10 = Day10()

    @Test
    fun partOne() {
        assertThat(day10.partOne()).isEqualTo(26397)
    }

    @Test
    fun partTwo() {
        assertThat(day10.partTwo()).isEqualTo(288957)
    }

    @ParameterizedTest(name = "[{index}] First illegal closing character in \"{0}\": {1}")
    @MethodSource("illegalClosingCharacterSamples")
    fun `find the first illegal closing character`(line: String, illegalChar: Char?) {
        val actual = Day10.SyntaxChecker.firstIllegalClosingCharIn(line)

        assertThat(actual).isEqualTo(illegalChar)
    }

    @ParameterizedTest(name = "[{index}] Missing closing characters in \"{0}\": {1}")
    @MethodSource("missingClosingCharacterSamples")
    fun `find the missing closing characters`(line: String, missingChars: String?) {
        val actual = Day10.SyntaxChecker.missingClosingCharsIn(line)

        assertThat(actual).isEqualTo(missingChars?.toList())
    }

    companion object {
        @JvmStatic
        fun illegalClosingCharacterSamples(): Stream<Arguments> {
            return listOf(
                arguments("[({(<(())[]>[[{[]{<()<>>", null),
                arguments("[(()[<>])]({[<{<<[]>>(", null),
                arguments("{([(<{}[<>[]}>{[]{[(<()>", "}"),
                arguments("(((({<>}<{<{<>}{[]{[]{}", null),
                arguments("[[<[([]))<([[{}[[()]]]", ")"),
                arguments("[{[{({}]{}}([{[{{{}}([]", "]"),
                arguments("{<[[]]>}<{[{[{[]{()[[[]", null),
                arguments("[<(<(<(<{}))><([]([]()", ")"),
                arguments("<{([([[(<>()){}]>(<<{{", ">"),
                arguments("<{([{{}}[<[[[<>{}]]]>[]]", null),
            ).stream()
        }

        @JvmStatic
        fun missingClosingCharacterSamples(): Stream<Arguments> {
            return listOf(
                arguments("[({(<(())[]>[[{[]{<()<>>", "}}]])})]"),
                arguments("[(()[<>])]({[<{<<[]>>(", ")}>]})"),
                arguments("{([(<{}[<>[]}>{[]{[(<()>", null),
                arguments("(((({<>}<{<{<>}{[]{[]{}", "}}>}>))))"),
                arguments("[[<[([]))<([[{}[[()]]]", null),
                arguments("[{[{({}]{}}([{[{{{}}([]", null),
                arguments("{<[[]]>}<{[{[{[]{()[[[]", "]]}}]}]}>"),
                arguments("[<(<(<(<{}))><([]([]()", null),
                arguments("<{([([[(<>()){}]>(<<{{", null),
                arguments("<{([{{}}[<[[[<>{}]]]>[]]", "])}>"),
            ).stream()
        }
    }
}