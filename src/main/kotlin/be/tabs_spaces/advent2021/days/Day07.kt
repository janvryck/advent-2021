package be.tabs_spaces.advent2021.days

import kotlin.math.abs

class Day07 : Day(7) {
    private val positions = inputString.split(",")
        .map { it.toInt() }
        .groupingBy { it }
        .eachCount()
        .map { Position(it.key, it.value) }

    override fun partOne() = targetPositionCandidates().minOf { target -> positions.sumOf { it.linearConsumptionTo(target) } }

    override fun partTwo() = targetPositionCandidates().minOf { target -> positions.sumOf { it.risingConsumptionTo(target) } }

    private fun targetPositionCandidates() = positions.minOf { it.coordinate }..positions.maxOf { it.coordinate }

    data class Position(val coordinate: Int, val crabs: Int) {
        private fun distanceTo(targetPosition: Int) = abs(targetPosition - coordinate)

        fun linearConsumptionTo(targetCoordinate: Int) = distanceTo(targetCoordinate).times(crabs)

        fun risingConsumptionTo(targetCoordinate: Int) = distanceTo(targetCoordinate).sumToNFromOne().times(crabs)

        private fun Int.sumToNFromOne() = this * (this + 1) / 2
    }
}