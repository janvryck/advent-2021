package be.tabs_spaces.advent2021.days

class Day09 : Day(9) {

    private val heightMap = HeightMap(inputList)

    override fun partOne() = heightMap
        .findLowPoints()
        .map { heightMap.heightAt(it) }
        .sumOf { it.toRiskLevel() }

    override fun partTwo() = heightMap.largestThreeBasins().productOf { it.size }

    class HeightMap(rawMap: List<String>) {
        private val yXZMap = rawMap.mapIndexed { y, xz -> y to xz.mapIndexed { x, z -> x to z.digitToInt() }.toMap() }.toMap()
        private val maxY = (yXZMap.size).dec()
        private val maxX = (yXZMap[0]?.size ?: -1).dec()

        fun findLowPoints(): List<Pair<Int, Int>> {
            return yXZMap.entries.flatMap { (y, xz) -> xz.entries.filter { (x, z) -> isLowPoint(x, y, z) }.map { y to it.key } }
        }

        fun heightAt(yX: Pair<Int, Int>) = yXZMap[yX.first]!![yX.second]!!

        fun largestThreeBasins() = findLowPoints().map { higherBasinPointsAround(it) }.sortedBy { it.size }.reversed().take(3)

        private fun higherBasinPointsAround(point: Pair<Int, Int>): Set<Pair<Int, Int>> {
            return setOf(point) + neighbouringPoints(point)
                .filter { heightAt(it) < 9 }
                .filter { heightAt(it) > heightAt(point) }
                .flatMap { higherBasinPointsAround(it) }
                .toSet()
        }

        private fun isLowPoint(x: Int, y: Int, z: Int): Boolean {
            return z < neighbouringPoints(y to x).minOf { this.heightAt(it) }
        }

        private fun neighbouringPoints(point: Pair<Int, Int>): List<Pair<Int, Int>> {
            val (originalY, originalX) = point
            val verticalNeighbours = listOfNotNull(if (originalY == 0) null else originalY - 1, if (originalY < maxY) originalY + 1 else null)
            val horizontalNeighbours = listOfNotNull(if (originalX == 0) null else originalX - 1, if (originalX < maxX) originalX + 1 else null)
            return verticalNeighbours.map { y -> y to originalX } + horizontalNeighbours.map { x -> originalY to x }
        }
    }

    private fun Int.toRiskLevel() = this.inc()

    private fun List<Collection<Any>>.productOf(transform: (Collection<Any>) -> Int) = this.map(transform).reduce { product, size -> product * size }

}