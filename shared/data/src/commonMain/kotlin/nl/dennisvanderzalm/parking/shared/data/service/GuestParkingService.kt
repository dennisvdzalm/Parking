package nl.dennisvanderzalm.parking.shared.data.service

import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.json.Json
import nl.dennisvanderzalm.parking.shared.core.models.DataSourceConfig
import nl.dennisvanderzalm.parking.shared.data.auth.TokenCache
import nl.dennisvanderzalm.parking.shared.data.model.create.CreateParkingReservationRequestDataModel
import nl.dennisvanderzalm.parking.shared.data.model.create.EndParkingReservationRequestDataModel
import nl.dennisvanderzalm.parking.shared.data.model.login.LicensePlateDataModel

class GuestParkingService(
    private val config: DataSourceConfig.Remote,
    private val tokenCache: TokenCache
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
            header("Token", tokenCache.token)

            host = config.parkingApiHost
            url {
                protocol = URLProtocol.HTTPS
            }
        }
        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) = println(message)
            }
            level = LogLevel.BODY
        }
    }

    suspend fun createReservation(
        dateFrom: LocalDateTime,
        dateUntil: LocalDateTime,
        licensePlateNumber: String,
        name: String?,
        permitMediaCode: String,
        permitMediaTypeId: Int
    ) = client.post<Unit>("/reservation/create") {
        body = CreateParkingReservationRequestDataModel(
            dateFrom,
            dateUntil,
            LicensePlateDataModel(licensePlateNumber, name),
            permitMediaCode,
            permitMediaTypeId
        )
    }

    suspend fun endParkingReservation(
        reservationId: String,
        permitMediaCode: String,
        permitMediaTypeId: Int
    ) = client.post<Unit>("/reservation/end") {
        body = EndParkingReservationRequestDataModel(
            reservationId,
            permitMediaCode,
            permitMediaTypeId
        )
    }
}
