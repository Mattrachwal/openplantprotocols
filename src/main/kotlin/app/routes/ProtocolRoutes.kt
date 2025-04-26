package app.routes

import app.models.ProtocolDTO
import app.repositories.ProtocolRepository
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.http.*

fun Route.protocolRoutes() {
    val repo = ProtocolRepository()

    route("/api/protocols") {
        get {
            call.respond(repo.getAll())
        }

        post {
            val dto = call.receive<ProtocolDTO>()
            val created = repo.create(dto)
            call.respond(HttpStatusCode.Created, created)
        }

        get("{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            val p  = id?.let { repo.getById(it) }
            if (p != null) call.respond(p)
            else call.respond(HttpStatusCode.NotFound, "Protocol not found")
        }
    }
}
