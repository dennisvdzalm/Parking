package nl.dennisvanderzalm.parking.ui.parkingoverview

sealed class ParkingReservationUiModel {

    abstract val licensePlateNumber: String?
    abstract val reservationId: Int

    data class Active(
        override val licensePlateNumber: String?,
        override val reservationId: Int,
        val timeLeft: () -> String
    ) : ParkingReservationUiModel()

    data class Expired(
        override val licensePlateNumber: String?,
        override val reservationId: Int,
        val duration: String
    ) : ParkingReservationUiModel()
}
