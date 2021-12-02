package be.tabs_spaces.advent2021.days

import be.tabs_spaces.advent2021.days.Day02.Position.ReferenceFrame.ABSOLUTE
import be.tabs_spaces.advent2021.days.Day02.Position.ReferenceFrame.AIMED

class Day02 : Day(2) {

    override fun partOne(): Any {
        val submarine = Submarine.absolutelyPositioned()

        move(submarine)
        return submarine.position.product
    }

    override fun partTwo(): Any {
        val submarine = Submarine.aimed()

        move(submarine)
        return submarine.position.product
    }

    private fun move(submarine: Submarine) {
        val instructions = inputList.map { it.split(" ") }.map { Instruction.from(it) }
        submarine.executeManoeuvres(instructions)
    }

    private class Submarine private constructor(
        val position: Position
    ) {
        companion object {
            fun absolutelyPositioned() = Submarine(Position())
            fun aimed() = Submarine(Position(referenceFrame = AIMED))
        }

        fun executeManoeuvres(instructions: List<Instruction>) = instructions.forEach {
            when (it.direction) {
                Instruction.Direction.FORWARD -> position.forward(it.units)
                Instruction.Direction.DOWN -> position.down(it.units)
                Instruction.Direction.UP -> position.up(it.units)
            }
        }
    }

    private data class Position(
        private val referenceFrame: ReferenceFrame = ABSOLUTE,
        private var horizontal: Int = 0,
        private var depth: Int = 0,
        private var aim: Int = 0,
    ) {
        val product
            get() = horizontal * depth

        fun forward(units: Int) {
            if (referenceFrame == AIMED) {
                depth += aim * units
            }
            horizontal += units
        }

        fun down(units: Int) {
            when (referenceFrame) {
                AIMED -> aim += units
                ABSOLUTE -> depth += units
            }
        }

        fun up(units: Int) {
            down(-1 * units)
        }

        enum class ReferenceFrame { ABSOLUTE, AIMED }
    }

    private data class Instruction private constructor(
        val direction: Direction,
        val units: Int
    ) {

        companion object {
            fun from(textualInstruction: List<String>) = Instruction(Direction.valueOf(textualInstruction[0].uppercase()), textualInstruction[1].toInt())
        }

        enum class Direction {
            FORWARD,
            UP,
            DOWN
        }
    }
}