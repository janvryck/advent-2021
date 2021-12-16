package be.tabs_spaces.advent2021.days

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource

class Day16Test {

    @ParameterizedTest
    @MethodSource("versionsForTransmissions")
    fun `Sums up versions for a transmission`(transmission: String, expectedVersion: Number) {
        val bits = Day16.BITS(transmission)

        assertThat(bits.cumulativeVersions()).isEqualTo(expectedVersion)
    }

    @ParameterizedTest
    @MethodSource("processedValuesForTransmissions")
    fun `Processes all packets for a transmission`(transmission: String, expectedValue: Number) {
        val bits = Day16.BITS(transmission)

        assertThat(bits.process()).isEqualTo(expectedValue)
    }

    companion object {
        @JvmStatic
        fun versionsForTransmissions() = listOf(
            arguments("38006F45291200", 1L + 6 + 2),
            arguments("620080001611562C8802118E34", 12L),
            arguments("8A004A801A8002F478", 16L),
            arguments("C0015000016115A2E0802F182340", 23L),
            arguments("A0016C880162017C3686B18A3D4780", 31L),
        )

        @JvmStatic
        fun processedValuesForTransmissions() = listOf(
            arguments("C200B40A82", 3L),
            arguments("04005AC33890", 54L),
            arguments("880086C3E88112", 7L),
            arguments("CE00C43D881120", 9L),
            arguments("D8005AC2A8F0", 1L),
            arguments("F600BC2D8F", 0L),
            arguments("9C005AC2F8F0", 0L),
            arguments("9C0141080250320F1802104A08", 1L),
        )
    }
}