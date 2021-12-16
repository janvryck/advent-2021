package be.tabs_spaces.advent2021.days

import be.tabs_spaces.advent2021.days.Day16.LengthTypeId.PACKET_COUNT
import be.tabs_spaces.advent2021.days.Day16.LengthTypeId.TOTAL_LENGTH
import be.tabs_spaces.advent2021.days.Day16.PacketType.*

class Day16 : Day(16) {

    override fun partOne() = BITS(inputString).cumulativeVersions()

    override fun partTwo() = BITS(inputString).process()

    class BITS(input: String) {
        private val rootPacket = parsePackets(input.hexToBinary()).first

        fun cumulativeVersions() = rootPacket.cumulativeVersions()

        fun process() = rootPacket.process()

        private fun parsePackets(binary: String): Pair<Packet, String> {
            var currentBinary = binary
            val version = currentBinary.take(3).also { currentBinary = currentBinary.drop(3) }.toInt(2)
            val type = currentBinary.take(3).also { currentBinary = currentBinary.drop(3) }.let { PacketType.from(it.toInt(2)) }

            return when (type) {
                LITERAL -> parseLiteral(version, currentBinary)
                else -> parseOperator(version, type, currentBinary)
            }
        }

        private fun parseLiteral(version: Int, binary: String): Pair<Literal, String> {
            var currentBinary = binary
            var literalBinary = ""
            do {
                val lastChunk = currentBinary.first().also { currentBinary = currentBinary.drop(1) } == '0'
                literalBinary += currentBinary.take(4).also { currentBinary = currentBinary.drop(4) }
            } while (!lastChunk)
            return Literal(version, literalBinary.toLong(2)) to currentBinary
        }

        private fun parseOperator(version: Int, type: PacketType, binary: String) =
            when (binary.first().let { LengthTypeId.from(it) }) {
                TOTAL_LENGTH -> parseByTotalLength(binary.drop(1), version, type)
                PACKET_COUNT -> parseBySubPacketCount(binary.drop(1), version, type)
            }

        private fun parseBySubPacketCount(binary: String, version: Int, type: PacketType): Pair<Operator, String> {
            var currentBinary = binary
            val count = currentBinary.take(11)
                .also { currentBinary = currentBinary.drop(11) }
                .toInt(2)

            val subPackets = mutableListOf<Packet>()
            repeat(count) {
                parsePackets(currentBinary).let { (packet, remainder) ->
                    subPackets.add(packet)
                    currentBinary = remainder
                }
            }
            return Operator(version, type, subPackets) to currentBinary
        }

        private fun parseByTotalLength(binary: String, version: Int, type: PacketType): Pair<Operator, String> {
            var currentBinary = binary
            val length = currentBinary.take(15)
                .also { currentBinary = currentBinary.drop(15) }
                .toInt(2)

            var content = currentBinary.take(length)
            val subPackets = mutableListOf<Packet>()
            while (content.any { it != '0' }) {
                parsePackets(content).let { (packet, remainder) ->
                    subPackets.add(packet)
                    content = remainder
                }
            }
            return Operator(version, type, subPackets) to currentBinary.drop(length)
        }
    }

    sealed class Packet(val version: Int, val type: PacketType) {
        abstract fun cumulativeVersions(): Long
        abstract fun process(): Long
    }

    class Literal(version: Int, private val literal: Long) : Packet(version, LITERAL) {

        override fun cumulativeVersions() = version.toLong()

        override fun process(): Long = literal
    }

    class Operator(version: Int, type: PacketType, private val subPackets: List<Packet>) : Packet(version, type) {

        override fun cumulativeVersions() = version + subPackets.sumOf { it.cumulativeVersions() }

        override fun process() = when (type) {
            SUM -> subPackets.sumOf { it.process() }
            PRODUCT -> subPackets.map { it.process() }.reduce(Long::times)
            MINIMUM -> subPackets.minOf { it.process() }
            MAXIMUM -> subPackets.maxOf { it.process() }
            GREATER_THAN -> subPackets.map { it.process() }.let { (first, second) -> if (first > second) 1L else 0L }
            LESS_THAN -> subPackets.map { it.process() }.let { (first, second) -> if (first < second) 1L else 0L }
            EQUAL_TO -> subPackets.map { it.process() }.let { (first, second) -> if (first == second) 1L else 0L }
            else -> throw IllegalStateException()
        }
    }

    enum class PacketType {
        SUM,
        PRODUCT,
        MINIMUM,
        MAXIMUM,
        LITERAL,
        GREATER_THAN,
        LESS_THAN,
        EQUAL_TO
        ;

        companion object {
            fun from(type: Int) = when (type) {
                0 -> SUM
                1 -> PRODUCT
                2 -> MINIMUM
                3 -> MAXIMUM
                4 -> LITERAL
                5 -> GREATER_THAN
                6 -> LESS_THAN
                7 -> EQUAL_TO
                else -> throw IllegalArgumentException("Unknown type $type")
            }
        }
    }

    enum class LengthTypeId {
        TOTAL_LENGTH,
        PACKET_COUNT
        ;

        companion object {
            fun from(value: Char) = when (value) {
                '0' -> TOTAL_LENGTH
                '1' -> PACKET_COUNT
                else -> throw IllegalArgumentException("Unknown length type")
            }
        }
    }

    companion object {
        private val hexToBinary = mapOf(
            '0' to "0000", '1' to "0001", '2' to "0010", '3' to "0011",
            '4' to "0100", '5' to "0101", '6' to "0110", '7' to "0111",
            '8' to "1000", '9' to "1001", 'A' to "1010", 'B' to "1011",
            'C' to "1100", 'D' to "1101", 'E' to "1110", 'F' to "1111",
        )

        fun String.hexToBinary() = this.map { hexToBinary[it] }.joinToString("")
    }
}