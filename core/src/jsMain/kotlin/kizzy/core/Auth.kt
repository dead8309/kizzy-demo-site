package kizzy.core

import io.ktor.client.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kizzy.core.entities.auth.AuthPayload
import kizzy.core.entities.auth.AuthResponse
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

val client = HttpClient {
    Logging {
        logger = object: Logger {
            override fun log(message: String) {
                console.log(message)
            }
        }
        level = LogLevel.ALL
    }
}
val json = Json {
    encodeDefaults = true
    ignoreUnknownKeys = true
}
suspend fun sendAuthRequest(authPayload: AuthPayload): AuthResponse {
    val response = client.post("https://discord.com/api/v9/auth/login") {
        setBody(json.encodeToString(authPayload))
        headers {
            append("origin","https://discord.com")
            append("Content-Type", "application/json")
        }
    }
    return json.decodeFromString(response.bodyAsText())
}