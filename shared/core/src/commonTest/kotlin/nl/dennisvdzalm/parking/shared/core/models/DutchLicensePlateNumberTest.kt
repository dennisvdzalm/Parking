package nl.dennisvdzalm.parking.shared.core.models

import nl.dennisvanderzalm.parking.shared.core.model.DutchLicensePlateNumber
import kotlin.test.*

class DutchLicensePlateNumberTest {

    @Test
    fun `Assert tryParse returns false when invalid license is used for input`() {
        assertFalse { DutchLicensePlateNumber.tryParse("S-0-S") }
    }

    @Test
    fun `Assert tryParse returns true when valid license is used for input`() {
        assertTrue { DutchLicensePlateNumber.tryParse("SR-850-S") }
    }

    @Test
    fun `Assert DutchLicensePlateNumber accepts formatted license numbers`() {
        val formatted = DutchLicensePlateNumber.parse("SR-850-S")
        assertEquals("SR-850-S", formatted.prettyNumber)
        assertEquals("SR850S", formatted.rawNumber)
    }

    @Test
    fun `Assert DutchLicensePlateNumber accepts formatted lowercase license numbers`() {
        val formattedLowerCase = DutchLicensePlateNumber.parse("sr-850-s")
        assertEquals("SR-850-S", formattedLowerCase.prettyNumber)
        assertEquals("SR850S", formattedLowerCase.rawNumber)
    }

    @Test
    fun `Assert DutchLicensePlateNumber accepts unformatted license numbers`() {
        val raw = DutchLicensePlateNumber.parse("SR850S")
        assertEquals("SR-850-S", raw.prettyNumber)
        assertEquals("SR850S", raw.rawNumber)
    }

    @Test
    fun `Assert DutchLicensePlateNumber accepts lower case input`() {
        val rawLowercase = DutchLicensePlateNumber.parse("sr850s")
        assertEquals("SR-850-S", rawLowercase.prettyNumber)
        assertEquals("SR850S", rawLowercase.rawNumber)
    }

    @Test
    fun `Assert plate numbers are prettified`() {
        verifyPrettyNumberIsMatches("SR850S", "SR-850-S")
        verifyPrettyNumberIsMatches("14SHF1", "14-SHF-1")
        verifyPrettyNumberIsMatches("52FVBB", "52-FV-BB")
    }

    @Test
    fun `Assert invalid input on parse throws`() {
        assertFails { DutchLicensePlateNumber.parse("1") }
    }

    private fun verifyPrettyNumberIsMatches(licensePlateNumber: String, expected: String) {
        val plate = DutchLicensePlateNumber.parse(licensePlateNumber)

        assertEquals(expected, plate.prettyNumber)
    }
}
