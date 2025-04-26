package app

import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.SchemaUtils

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.serialization.jackson.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import com.fasterxml.jackson.databind.*
import org.jetbrains.exposed.sql.Database


import app.models.MediaFormulas
import app.models.Protocols

import app.routes.mediaFormulaRoutes
import app.routes.protocolRoutes

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module).start(wait = true)
}

fun Application.module() {
    // Connect to the database
    val db = Database.connect(
        "jdbc:postgresql://localhost:5433/openplantprotocols",
        driver = "org.postgresql.Driver",
        user = "openpp",
        password = "openpppass"
    )

    transaction(db) {
        SchemaUtils.create(MediaFormulas)
    }
   

    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
        }
    }

    routing {
        get("/") {
            call.respond(mapOf("message" to "OpenPlantProtocols API is running 🌱"))
        }

        mediaFormulaRoutes()
        protocolRoutes()
    }
}
