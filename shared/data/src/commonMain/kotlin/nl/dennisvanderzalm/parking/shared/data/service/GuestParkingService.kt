package nl.dennisvanderzalm.parking.shared.data.service


import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
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

    private val client = HttpClient {
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    encodeDefaults = true
                }
            )
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
    ) = client.post("DVSWebAPI/api/reservation/create") {
        setBody(
            CreateParkingReservationRequestDataModel(
                dateFrom,
                dateUntil,
                LicensePlateDataModel(licensePlateNumber, name),
                sessionManager.permitCode!!,
                1
            )
        )
    }

    suspend fun endParkingReservation(
        reservationId: Int
    ) = client.post("DVSWebAPI/api/reservation/end") {
        setBody(
            EndParkingReservationRequestDataModel(
                reservationId,
                sessionManager.permitCode!!,
                1
            )
        )
    }

    suspend fun getParkingHistory(): List<ReservationItemDataModel> {
        val response: LoginResponseDataModel = client.post("DVSWebAPI/api/login/getbase").body()
        return response
            .permits
            .first()
            .permitMedias
            .first()
            .historyDataModel.reservations.items
    }


    suspend fun getAddressBook(): List<LicensePlateDataModel> {

        val response: LoginResponseDataModel = client.post("DVSWebAPI/api/login/getbase").body()
        return response
            .permits
            .first()
            .permitMedias
            .first()
            .licensePlates
    }

}
