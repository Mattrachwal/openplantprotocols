package app.routes

import app.models.MediaFormulaDTO
import app.repositories.MediaFormulaRepository
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import io.ktor.http.*

fun Route.mediaFormulaRoutes() {
    val repo = MediaFormulaRepository()

    route("/api/formulas") {
        get {
            call.respond(repo.getAll())
        }

        post {
            val payload = call.receive<MediaFormulaDTO>()
            val created = repo.create(payload)
            call.respond(HttpStatusCode.Created, created)
        }

        get("{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            val formula = id?.let { repo.getById(it) }

            if (formula != null) {
                call.respond(formula)
            } else {
                call.respond(HttpStatusCode.NotFound, "Formula not found")
            }
        }
    }
}
