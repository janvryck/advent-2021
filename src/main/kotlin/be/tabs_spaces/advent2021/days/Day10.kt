package be.tabs_spaces.advent2021.days

class Day10 : Day(10) {

    override fun partOne() = inputList
        .mapNotNull { SyntaxChecker.firstIllegalClosingCharIn(it) }
        .sumOf { Syntax.syntaxErrorScoreFor(it) }

    override fun partTwo() = inputList
        .mapNotNull { SyntaxChecker.missingClosingCharsIn(it) }
        .map { it.fold(0L) { score, character -> 5 * score + Syntax.autocompleteScoreFor(character) } }
        .sorted()
        .run { this[size / 2] }

    object Syntax {
        private val syntax = mapOf(
            ('(' to ')') to Scores(3, 1),
            ('[' to ']') to Scores(57, 2),
            ('{' to '}') to Scores(1197, 3),
            ('<' to '>') to Scores(25137, 4),
        )

        val startingCharacters = syntax.keys.map { it.first }
        val endingCharacters = syntax.keys.map { it.second }

        fun syntaxForStart(char: Char) = syntax.keys.first { it.first == char }

        fun syntaxErrorScoreFor(char: Char) = findByClosingChar(char).value.syntaxErrorScore
        fun autocompleteScoreFor(char: Char) = findByClosingChar(char).value.autocompleteScore

        private fun findByClosingChar(char: Char) = syntax.entries.first { it.key.second == char }

        data class Scores(val syntaxErrorScore: Int, val autocompleteScore: Int)
    }

    object SyntaxChecker {
        fun firstIllegalClosingCharIn(line: String): Char? {
            val syntaxStack = mutableListOf<Pair<Char, Char>>()
            line.forEach {
                when (it) {
                    in Syntax.startingCharacters -> syntaxStack.add(Syntax.syntaxForStart(it))
                    in Syntax.endingCharacters -> if (syntaxStack.last().second == it) syntaxStack.removeLast() else return it
                }
            }
            return null
        }

        fun missingClosingCharsIn(line: String): List<Char>? {
            val syntaxStack = mutableListOf<Pair<Char, Char>>()
            line.forEach {
                when (it) {
                    in Syntax.startingCharacters -> syntaxStack.add(Syntax.syntaxForStart(it))
                    in Syntax.endingCharacters -> if (syntaxStack.last().second == it) syntaxStack.removeLast() else return null
                }
            }
            return syntaxStack.reversed().map { it.second }
        }
    }
}