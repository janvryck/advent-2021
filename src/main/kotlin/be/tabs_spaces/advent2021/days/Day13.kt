package be.tabs_spaces.advent2021.days

import be.tabs_spaces.advent2021.days.Day13.Axis.X
import be.tabs_spaces.advent2021.days.Day13.Axis.Y

class Day13 : Day(13) {

    private val transparentPaper = TransparentPaper.parse(inputList)


    override fun partOne(): Any {
        return transparentPaper.fold(1).size
    }

    override fun partTwo(): Any {
        return transparentPaper.fold().visualize()
    }

    class TransparentPaper private constructor(
        private val dots: List<Point>,
        private val foldingInstructions: List<FoldingInstruction>
    ) {

        companion object {
            fun parse(input: List<String>): TransparentPaper {
                val foldingInstructions = parseFoldingInstructions(input)
                return TransparentPaper(parsePoints(input), foldingInstructions)
            }

            private fun parsePoints(input: List<String>) = input
                .takeWhile { it.isNotBlank() }
                .map { it.split(",") }
                .map { (x, y) -> Point(x.toInt(), y.toInt()) }

            private fun parseFoldingInstructions(input: List<String>) = input.takeLastWhile { it.isNotBlank() }
                .map { it.replace("fold along ", "").split("=") }
                .map { (axis, line) -> FoldingInstruction(Axis.from(axis), line.toInt()) }
        }

        fun fold(times: Int = 1) = fold(foldingInstructions.take(times))

        fun fold() = fold(foldingInstructions)

        private fun fold(instructions: List<FoldingInstruction>) = instructions.fold(dots) { dots, instruction ->
            dots.mapNotNull { it.fold(instruction) }.distinct()
        }.distinct()
    }

    data class Point(val x: Int, val y: Int) {
        fun fold(instruction: FoldingInstruction): Point? = when (instruction.axis) {
            X -> foldX(instruction.foldLine)
            Y -> foldY(instruction.foldLine)
        }

        private fun foldX(foldLine: Int): Point? = when (x) {
            in 0 until foldLine -> this.copy()
            foldLine -> null
            else -> this.copy(x = offset(x, foldLine))
        }

        private fun foldY(foldLine: Int): Point? = when (y) {
            in 0 until foldLine -> this.copy()
            foldLine -> null
            else -> this.copy(y = offset(y, foldLine))
        }

        private fun offset(original: Int, foldLine: Int): Int = original - 2 * (original - foldLine)
    }

    data class FoldingInstruction(val axis: Axis, val foldLine: Int)

    enum class Axis {
        X,
        Y;

        companion object {
            fun from(raw: String) = when (raw.uppercase()) {
                "X" -> X
                "Y" -> Y
                else -> throw IllegalArgumentException("Unknown axis")
            }
        }
    }

    private fun List<Point>.visualize() = (0..this.maxOf { it.y }).joinToString(separator = "", prefix = "\n") { y ->
        (0..this.maxOf { it.x }).joinToString(separator = "", postfix = "\n") { x ->
            firstOrNull { it.x == x && it.y == y }?.let { "#" } ?: "."
        }
    }
}