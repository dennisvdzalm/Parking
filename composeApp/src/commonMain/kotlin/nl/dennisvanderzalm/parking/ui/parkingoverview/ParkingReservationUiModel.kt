package nl.dennisvanderzalm.parking.ui.parkingoverview

sealed class ParkingReservationUiModel(val key: String) {

    abstract val reservationId: Int

    data class Active(
        val licensePlateNumber: String,
        override val reservationId: Int,
        val timeLeft: String,
    ) : ParkingReservationUiModel("$reservationId&Active")

    data class Expired(
        val licensePlateNumber: String?,
        override val reservationId: Int,
        val duration: String
    ) : ParkingReservationUiModel("$reservationId&Expired")
}
