package app.repositories

import app.models.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime

class MediaFormulaRepository {
    fun getAll(): List<MediaFormulaDTO> = transaction {
        MediaFormulas.selectAll().map {
            MediaFormulaDTO(
                id = it[MediaFormulas.id].value,
                name = it[MediaFormulas.name],
                description = it[MediaFormulas.description],
                createdAt = it[MediaFormulas.createdAt].toString()
            )
        }
    }

    fun create(dto: MediaFormulaDTO): MediaFormulaDTO = transaction {
        val id = MediaFormulas.insertAndGetId {
            it[name] = dto.name
            it[description] = dto.description
            it[createdAt] = DateTime.now()
        }.value

        getById(id)!!
    }

    fun getById(id: Int): MediaFormulaDTO? = transaction {
        MediaFormulas.select { MediaFormulas.id eq id }
            .map {
                MediaFormulaDTO(
                    id = it[MediaFormulas.id].value,
                    name = it[MediaFormulas.name],
                    description = it[MediaFormulas.description],
                    createdAt = it[MediaFormulas.createdAt].toString()
                )
            }.singleOrNull()
    }
}
