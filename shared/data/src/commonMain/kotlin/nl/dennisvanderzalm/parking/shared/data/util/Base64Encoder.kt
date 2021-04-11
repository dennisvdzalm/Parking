package nl.dennisvanderzalm.parking.shared.data.util

interface Base64Encoder {

    fun encode(value: String): String

    fun decode(value: String): String
}
