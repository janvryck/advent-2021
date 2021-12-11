package be.tabs_spaces.advent2021.days

import kotlin.also as and
import kotlin.let as then

class Day11 : Day(11) {

    override fun partOne() = Cavern(inputList).iterate(100)

    override fun partTwo() = Cavern(inputList).optimalStep()

    class Cavern(rawInput: List<String>) {
        private val octopuses = rawInput.indices.flatMap { y ->
            rawInput[y].indices.map { x -> Octopus(Point(x, y), rawInput[y][x].digitToInt()) }
        }
        private val xMax = octopuses.maxOf { it.position.x }
        private val yMax = octopuses.maxOf { it.position.y }

        fun iterate(times: Int) = (1..times).sumOf { step() }

        fun optimalStep() = generateSequence { step() }.takeWhile { it < 100 }.count().inc()

        private fun step(): Int = octopuses
            .forEach { it.increaseEnergyLevel() }
            .then { countFlashes().and { expendEnergy() } }

        private fun countFlashes() =
            generateSequence { octopuses.count { it.willFlash() }.and { propagateFlashes() } }
                .takeWhile { it > 0 }
                .sum()

        private fun propagateFlashes() = octopuses.filter { it.willFlash() }.forEach { it.flash(neighbours(it.position)) }

        private fun neighbours(position: Point) = octopuses.filter { it.position in position.neighbours(xMax, yMax) }

        private fun expendEnergy() = octopuses.filter { it.hasFlashed() }.forEach { it.energyExpended() }
    }

    private data class Octopus(val position: Point, private var energyLevel: Int) {
        fun increaseEnergyLevel() {
            energyLevel++
        }

        fun energyExpended() {
            energyLevel = 0
        }

        fun willFlash() = energyLevel == 10
        fun hasFlashed() = energyLevel > 10
        fun flash(neighbours: List<Octopus>) {
            increaseEnergyLevel()
            neighbours
                .filterNot { it.willFlash() }
                .forEach { it.increaseEnergyLevel() }
        }
    }

    private data class Point(val x: Int, val y: Int) {
        fun neighbours(xMax: Int, yMax: Int) = listOf(x - 1, x, x + 1)
            .filter { it in 0..xMax }
            .flatMap { x ->
                listOf(y - 1, y, y + 1)
                    .filter { it in 0..yMax }
                    .map { y -> Point(x, y) }
            }.filter { neighbour -> neighbour != this }
    }
}