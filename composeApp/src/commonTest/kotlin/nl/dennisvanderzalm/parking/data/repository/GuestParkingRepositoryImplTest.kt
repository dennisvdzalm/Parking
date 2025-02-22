package nl.dennisvanderzalm.parking.data.repository

import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import nl.dennisvanderzalm.parking.core.model.DutchLicensePlateNumber
import nl.dennisvanderzalm.parking.core.model.ParkingHistoryItem
import nl.dennisvanderzalm.parking.core.model.ParkingZone
import nl.dennisvanderzalm.parking.data.source.GuestParkingDataSource
import nl.dennisvanderzalm.parking.data.source.local.LocalPaidParkingDataSource
import kotlin.test.Test
import kotlin.test.assertTrue

class GuestParkingRepositoryImplTest {

    private val repository =
        GuestParkingRepositoryImpl(
            mock,
            LocalPaidParkingDataSource()
        )

    private val timeZone = TimeZone.of("Europe/Amsterdam")

    @Test
    fun `Test no reservation necessary`() {
        val start = LocalDateTime(2021, 1, 5, 1, 0).toInstant(timeZone)
        val end = LocalDateTime(2021, 1, 5, 2, 0).toInstant(timeZone)

        val reservations = repository.resolveParkingReservations(
            true,
            start,
            end,
            DutchLicensePlateNumber.parse("SR850S"),
            "Dennis",
            ParkingZone.ZoneB1
        )

        assertTrue { reservations.isEmpty() }
    }

    @Test
    fun `Test one reservation necessary`() {
        val start = LocalDateTime(2021, 1, 5, 12, 0).toInstant(timeZone)
        val end = LocalDateTime(2021, 1, 5, 15, 0).toInstant(timeZone)

        val reservations = repository.resolveParkingReservations(
            true,
            start,
            end,
            DutchLicensePlateNumber.Companion.parse("SR850S"),
            "Dennis",
            ParkingZone.ZoneA
        )

        assertTrue { reservations.size == 1 }
    }

    @Test
    fun `Test two reservation necessary`() {
        val start = LocalDateTime(2021, 4, 12, 16, 34)
            .toInstant(timeZone)

        val end = LocalDateTime(2021, 4, 13, 11, 35)
            .toInstant(timeZone)

        val reservations = repository.resolveParkingReservations(
            true,
            start,
            end,
            DutchLicensePlateNumber.Companion.parse("SR850S"),
            "Dennis",
            ParkingZone.ZoneA
        )

        assertTrue { reservations.size == 2 }
    }

    @Test
    fun `Test three reservation necessary`() {
        val start = LocalDateTime(2021, 1, 5, 12, 0)
            .toInstant(timeZone)

        val end = LocalDateTime(2021, 1, 5, 15, 0)
            .toInstant(timeZone)
            .plus(2, DateTimeUnit.DAY, timeZone)

        val reservations = repository.resolveParkingReservations(
            true,
            start,
            end,
            DutchLicensePlateNumber.Companion.parse("SR850S"),
            "Dennis",
            ParkingZone.ZoneA
        )

        assertTrue { reservations.size == 3 }
    }
}

private val mock = object : GuestParkingDataSource {
    override suspend fun createParkingSessions(
        from: Instant,
        until: Instant,
        licensePlate: DutchLicensePlateNumber,
        name: String
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun endParkingSessions(reservationId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun getParkingHistory(): List<ParkingHistoryItem> {
        TODO("Not yet implemented")
    }

}