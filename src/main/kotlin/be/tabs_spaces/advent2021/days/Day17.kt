package be.tabs_spaces.advent2021.days

import be.tabs_spaces.advent2021.util.sumToNFromOne
import kotlin.math.abs

class Day17 : Day(17) {
    private val trajectoryControl = TargetArea.from(inputString).let { targetArea -> TrajectoryControl(targetArea) }

    override fun partOne() = trajectoryControl.maxVerticalSpeed().sumToNFromOne()

    override fun partTwo() = trajectoryControl.targetTrajectories().size

    data class TrajectoryControl(val targetArea: TargetArea) {

        fun targetTrajectories() = launchVelocityCandidates().filter { it.hits(targetArea) }

        private fun launchVelocityCandidates() = (1..maxHorizontalSpeed()).flatMap { x ->
            (minVerticalSpeed()..maxVerticalSpeed()).map { y -> Velocity(x, y) }
        }

        private fun minVerticalSpeed() = minSpeed(Point::y)

        fun maxVerticalSpeed() = maxSpeed(Point::y) { abs(it + 1) }

        private fun maxHorizontalSpeed() = maxSpeed(Point::x)

        private fun maxSpeed(axis: (Point) -> Int, transform: ((Int) -> Int) = { it }): Int {
            return listOf(
                axis(targetArea.bottomRight),
                axis(targetArea.upperLeft),
            ).maxOf(transform)
        }

        private fun minSpeed(axis: (Point) -> Int, transform: ((Int) -> Int) = { it }): Int {
            return listOf(
                axis(targetArea.bottomRight),
                axis(targetArea.upperLeft),
            ).minOf(transform)
        }
    }

    data class TargetArea(val upperLeft: Point, val bottomRight: Point) {
        companion object {
            fun from(rawInput: String): TargetArea {
                val (x, y) = rawInput.drop("target area: ".length).split(", ")
                val (xMin, xMax) = x.drop("x=".length).split("..").map { it.toInt() }
                val (yMin, yMax) = y.drop("y=".length).split("..").map { it.toInt() }

                return TargetArea(Point(xMin, yMax), Point(xMax, yMin))
            }
        }

        fun contains(point: Point) = point.let { (x, y) ->
            x in upperLeft.x..bottomRight.x && y in bottomRight.y..upperLeft.y
        }

        fun overshot(point: Point) = point.let { (x, y) -> x > bottomRight.x || y < bottomRight.y }
    }

    data class Velocity(val dX: Int, val dY: Int) {
        fun hits(targetArea: TargetArea): Boolean {
            var currentHorizontalSpeed = dX
            var currentVerticalSpeed = dY
            var point = Point(0, 0)
            do {
                point = point.let { (x, y) -> Point(x + currentHorizontalSpeed, y + currentVerticalSpeed) }

                if (targetArea.contains(point)) return true
                currentHorizontalSpeed = maxOf(0, currentHorizontalSpeed - 1)
                currentVerticalSpeed -= 1
            } while (!targetArea.overshot(point))
            return false
        }

    }

    data class Point(val x: Int, val y: Int)
}
