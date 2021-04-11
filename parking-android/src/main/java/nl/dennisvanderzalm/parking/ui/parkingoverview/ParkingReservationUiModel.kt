package nl.dennisvanderzalm.parking.ui.parkingoverview

sealed class ParkingReservationUiModel {

    abstract val reservationId: Int

    data class Active(
        val licensePlateNumber: String,
        override val reservationId: Int,
        val timeLeft: String
    ) : ParkingReservationUiModel()

    data class Expired(
        val licensePlateNumber: String?,
        override val reservationId: Int,
        val duration: String
    ) : ParkingReservationUiModel()
}
