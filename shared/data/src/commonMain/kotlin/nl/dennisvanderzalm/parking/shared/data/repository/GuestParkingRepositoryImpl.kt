package nl.dennisvanderzalm.parking.shared.data.repository

import kotlinx.datetime.*
import nl.dennisvanderzalm.parking.shared.core.model.DutchLicensePlateNumber
import nl.dennisvanderzalm.parking.shared.core.model.ParkingHistoryItem
import nl.dennisvanderzalm.parking.shared.core.model.ParkingReservation
import nl.dennisvanderzalm.parking.shared.core.repository.GuestParkingRepository
import nl.dennisvanderzalm.parking.shared.data.source.GuestParkingDataSource
import nl.dennisvanderzalm.parking.shared.data.source.PaidParkingDataSource
import kotlin.time.ExperimentalTime

class GuestParkingRepositoryImpl(
    private val parkingDataSource: GuestParkingDataSource,
    private val paidParkingDataSource: PaidParkingDataSource
) : GuestParkingRepository {

    private val timeZone = TimeZone.of("Europe/Amsterdam")

    override suspend fun createParkingReservation(reservation: ParkingReservation) =
        parkingDataSource.createParkingSessions(
            reservation.start,
            reservation.end,
            reservation.licensePlate,
            reservation.name
        )


    override suspend fun endParkingReservation(reservationId: Int) = parkingDataSource.endParkingSessions(reservationId)

    override suspend fun getParkingHistory(): List<ParkingHistoryItem> = parkingDataSource.getParkingHistory()

    @OptIn(ExperimentalTime::class)
    override fun resolveParkingReservations(
        start: Instant,
        end: Instant,
        licensePlate: DutchLicensePlateNumber,
        name: String
    ): List<ParkingReservation> {
        val localStartDateTime = start.toLocalDateTime(timeZone)
        val localEndDateTime = end.toLocalDateTime(timeZone)

        val localStartDate = localStartDateTime.toLocalDate()
        val localEndDate = localEndDateTime.toLocalDate()

        val daysFromStart = localStartDate.until(localEndDate, DateTimeUnit.DAY)
        val reservationList = mutableListOf<ParkingReservation>()

        for (i in 0..daysFromStart) {
            val day = localStartDate.plus(i, DateTimeUnit.DAY)
            val paidParkingTimeframe = paidParkingDataSource.getPaidParkingHours(day)
            val paidParkingStart = paidParkingTimeframe.start
            val paidParkingEnd = paidParkingTimeframe.end

            // No paid parking necessary
            if (localStartDateTime < paidParkingStart && localEndDateTime < paidParkingStart ||
                localStartDateTime > paidParkingEnd && localEndDateTime > paidParkingEnd
            ) return reservationList

            // Exactly within timeframe, one parking reservation necessary
            if (localStartDateTime > paidParkingStart && localEndDateTime < paidParkingEnd) {
                reservationList.add(
                    ParkingReservation(localStartDateTime.toInstant(), localEndDateTime.toInstant(), licensePlate, name)
                )
            }
            // Starting time can be adjusted to match paid parking start time
            else if (localStartDateTime < paidParkingStart && localEndDateTime < paidParkingEnd) {
                reservationList.add(
                    ParkingReservation(paidParkingStart.toInstant(), localEndDateTime.toInstant(), licensePlate, name)
                )
            }
            // End time can be adjusted to match paid parking end time
            else if (localStartDateTime > paidParkingStart && localEndDateTime > paidParkingEnd) {
                reservationList.add(
                    ParkingReservation(localStartDateTime.toInstant(), paidParkingEnd.toInstant(), licensePlate, name)
                )
            }
            // Both times reach outside of paid parking times, adjusting both to paid parking boundaries
            else if (localStartDateTime < paidParkingStart && localEndDateTime > paidParkingEnd) {
                reservationList.add(
                    ParkingReservation(paidParkingStart.toInstant(), paidParkingEnd.toInstant(), licensePlate, name)
                )
            }
        }

        return reservationList
    }
}


private fun LocalDateTime.toLocalDate() = LocalDate(year, monthNumber, dayOfMonth)

private fun LocalDateTime.toInstant() = toInstant(TimeZone.of("Europe/Amsterdam"))
