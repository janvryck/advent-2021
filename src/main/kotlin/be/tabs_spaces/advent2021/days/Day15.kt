package be.tabs_spaces.advent2021.days

import java.util.*

class Day15 : Day(15) {

    override fun partOne() = Cave(inputList).lowestRiskPath(Point(0, 0))

    override fun partTwo() = Cave(inputList, expand = true).lowestRiskPath(Point(0, 0))

    class Cave(rawInput: List<String>, expand: Boolean = false) {
        private val riskLevelsByPoint = rawInput.indices
            .flatMap { y -> rawInput[y].indices.map { x -> Point(x, y) } }
            .associateWith { point -> rawInput[point.y][point.x].digitToInt() }
            .let { if (expand) it.expand() else it }
        private val xMax by lazy { riskLevelsByPoint.keys.maxOf { it.x } }
        private val yMax by lazy { riskLevelsByPoint.keys.maxOf { it.y } }

        fun lowestRiskPath(origin: Point): Int {
            val accumulatedRisk = mutableMapOf<Point, Int>()
            val visited = mutableSetOf(origin)

            val visiting = PriorityQueue<Point> { left, right -> (accumulatedRisk[left] ?: Int.MAX_VALUE).compareTo(accumulatedRisk[right] ?: Int.MAX_VALUE) }
            visiting.add(origin)

            while (visiting.isNotEmpty()) {
                visiting.poll()
                    .also { visited.add(it) }
                    .let { risksForNeighboursOf(it, accumulatedRisk[it] ?: 0) }
                    .filterNot { (neighbour, _) -> visited.contains(neighbour) }
                    .filter { (neighbour, risk) -> risk < (accumulatedRisk[neighbour] ?: Int.MAX_VALUE) }
                    .forEach { (neighbour, risk) ->
                        accumulatedRisk[neighbour] = risk
                        visiting.add(neighbour)
                    }
            }
            return accumulatedRisk[Point(xMax, yMax)]!!
        }

        private fun risksForNeighboursOf(point: Point, cost: Int) = point.neighbours(xMax, yMax)
            .map { current -> current to cost + riskLevelsByPoint[current]!! }

        private fun Map<Point, Int>.expand(): Map<Point, Int> {
            val xMax = keys.maxOf { it.x }
            val yMax = keys.maxOf { it.y }
            return (0 until 5).flatMap { offsetX ->
                (0 until 5).flatMap { offsetY ->
                    entries.map {
                        val (x, y) = it.key
                        val offsetRisk = offsetX + offsetY + it.value
                        val newRisk = if (offsetRisk > 9) offsetRisk - 9 else offsetRisk
                        Point(x = x + offsetX * (xMax + 1), y = y + offsetY * (yMax + 1)) to newRisk
                    }
                }
            }.toMap()
        }
    }

    data class Point(val x: Int, val y: Int) {
        fun neighbours(xMax: Int, yMax: Int) = listOf(x - 1, x + 1).filter { it in 0..xMax }.map { Point(it, y) } +
                listOf(y - 1, y + 1).filter { it in 0..yMax }.map { Point(x, it) }
    }
}