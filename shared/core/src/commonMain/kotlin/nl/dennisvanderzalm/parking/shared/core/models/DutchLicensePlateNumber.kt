package nl.dennisvanderzalm.parking.shared.core.models

class DutchLicensePlateNumber private constructor(val rawNumber: String, val prettyNumber: String) {

    override fun toString(): String = prettyNumber

    companion object {
        private val patterns = listOf(
            Regex("^([A-Z]{2})([0-9]{2})([0-9]{2})$"),
            Regex("^([0-9]{2})([0-9]{2})([A-Z]{2})$"),
            Regex("^([0-9]{2})([A-Z]{2})([0-9]{2})$"),
            Regex("^([BDFGHJKLMNPRSTVWXYZ]{2})([0-9]{2})([BDFGHJKLMNPRSTVWXYZ]{2})$"),
            Regex("^([BDFGHJKLMNPRSTVWXYZ]{2})([BDFGHJKLMNPRSTVWXYZ]{2})([0-9]{2})$"),
            Regex("^([0-9]{2})([BDFGHJKLMNPRSTVWXYZ]{2})([BDFGHJKLMNPRSTVWXYZ]{2})$"),
            Regex("^([0-9]{2})([BDFGHJKLMNPRSTVWXYZ]{3})([0-9]{1})$"),
            Regex("^([0-9]{1})([BDFGHJKLMNPRSTVWXYZ]{3})([0-9]{2})$"),
            Regex("^([BDFGHJKLMNPRSTVWXYZ]{2})([0-9]{3})([BDFGHJKLMNPRSTVWXYZ]{1})$"),
            Regex("^([BDFGHJKLMNPRSTVWXYZ]{1})([0-9]{3})([BDFGHJKLMNPRSTVWXYZ]{2})$"),
            Regex("^((?!PVV|VVD|SGP)[BDFGHJKLMNPRSTVWXYZ]{3})([0-9]{2})([BDFGHJKLMNPRSTVWXYZ]{1})$")
        )

        fun parse(number: String) = parseLicense(number)

        fun tryParse(number: String): Boolean = try {
            parseLicense(number)
            true
        } catch (e: IllegalArgumentException) {
            false
        }

        private fun parseLicense(number: String): DutchLicensePlateNumber {
            val rawNumber = number
                .replace("-", "")
                .toUpperCase()

            val pattern = requireNotNull(patterns.firstOrNull { it.matches(rawNumber) }) {
                "Invalid license plate number $number"
            }

            val prettyNumber = rawNumber.replace(pattern) {
                it.groupValues
                    .subList(1, it.groupValues.size) // sublist from index 1 as index 0 is always the complete match
                    .joinToString("-")
            }

            return DutchLicensePlateNumber(rawNumber, prettyNumber)
        }
    }

    override fun hashCode(): Int = rawNumber.hashCode()
}

fun String.toLicensePlateNumber() = DutchLicensePlateNumber.parse(this)
fun String.isLicensePlate() = DutchLicensePlateNumber.tryParse(this)
