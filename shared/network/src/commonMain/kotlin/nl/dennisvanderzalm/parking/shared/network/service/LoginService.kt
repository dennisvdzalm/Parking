package nl.dennisvanderzalm.parking.shared.network.service

import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.features.auth.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.json.Json
import nl.dennisvanderzalm.parking.shared.core.models.DataSourceConfig
import nl.dennisvanderzalm.parking.shared.network.models.login.LoginRequestDataModel
import nl.dennisvanderzalm.parking.shared.network.models.login.LoginResponseDataModel

class LoginService(private val config: DataSourceConfig.Remote) {

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
            host = config.parkingApiHost
            url {
                protocol = URLProtocol.HTTPS
            }
        }
        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    println(message)
                }

            }
            level = LogLevel.BODY
        }
    }

    suspend fun login(username: CharSequence, password: CharSequence): LoginResponseDataModel = client.post("/login") {
        body = LoginRequestDataModel(identifier = username.toString(), password = password.toString())
    }
}
