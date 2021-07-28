package nl.dennisvanderzalm.parking.shared.data.service

import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.datetime.Instant
import kotlinx.serialization.json.Json
import nl.dennisvanderzalm.parking.shared.core.model.DataSourceConfig
import nl.dennisvanderzalm.parking.shared.data.model.create.CreateParkingReservationRequestDataModel
import nl.dennisvanderzalm.parking.shared.data.model.create.EndParkingReservationRequestDataModel
import nl.dennisvanderzalm.parking.shared.data.model.login.LicensePlateDataModel
import nl.dennisvanderzalm.parking.shared.data.model.login.LoginResponseDataModel
import nl.dennisvanderzalm.parking.shared.data.model.login.ReservationItemDataModel
import nl.dennisvanderzalm.parking.shared.data.session.SessionManager

class GuestParkingService(
    private val config: DataSourceConfig.Remote,
    private val sessionManager: SessionManager
) {
    private val json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
    }

    private val client = HttpClient {
        install(JsonFeature) {
            serializer = KotlinxSerializer(json)
        }
        defaultRequest {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            header(HttpHeaders.Authorization, "Token ${sessionManager.token}")

            host = config.parkingApiHost
            url {
                protocol = URLProtocol.HTTPS
            }
        }
        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) = println(message)
            }
            level = LogLevel.ALL
        }
    }

    suspend fun createReservation(
        dateFrom: Instant,
        dateUntil: Instant,
        licensePlateNumber: String,
        name: String?
    ) = client.post<String>("/reservation/create") {
        body = CreateParkingReservationRequestDataModel(
            dateFrom,
            dateUntil,
            LicensePlateDataModel(licensePlateNumber, name),
            sessionManager.permitCode!!,
            1
        )
    }

    suspend fun endParkingReservation(
        reservationId: Int
    ) = client.post<String>("/reservation/end") {
        body = EndParkingReservationRequestDataModel(
            reservationId,
            sessionManager.permitCode!!,
            1
        )
    }

    suspend fun getParkingHistory(): List<ReservationItemDataModel> =
        client.post<LoginResponseDataModel>("/login/getbase")
            .permits
            .first()
            .permitMedias
            .first()
            .historyDataModel.reservations.items

    suspend fun getAddressBook(): List<LicensePlateDataModel> =
        client.post<LoginResponseDataModel>("/login/getbase")
            .permits
            .first()
            .permitMedias
            .first()
            .licensePlates
}
