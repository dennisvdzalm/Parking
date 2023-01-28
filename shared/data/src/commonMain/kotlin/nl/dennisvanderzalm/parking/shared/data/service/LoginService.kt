package nl.dennisvanderzalm.parking.shared.data.service

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.http.ContentType.Application.Json
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import nl.dennisvanderzalm.parking.shared.core.model.DataSourceConfig
import nl.dennisvanderzalm.parking.shared.data.model.login.LoginRequestDataModel
import nl.dennisvanderzalm.parking.shared.data.model.login.LoginResponseDataModel

class LoginService(private val config: DataSourceConfig.Remote) {

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
            header(HttpHeaders.ContentType, Json)
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

    suspend fun login(username: CharSequence, password: CharSequence): LoginResponseDataModel {
        val response: HttpResponse = client.post("DVSWebAPI/api/login") {
            setBody(LoginRequestDataModel(identifier = username.toString(), password = password.toString()))
        }
        return response.body()
    }
}
