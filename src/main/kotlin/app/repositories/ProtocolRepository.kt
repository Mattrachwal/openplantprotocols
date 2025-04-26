package app.repositories

import app.models.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime

class ProtocolRepository {
    fun getAll(): List<ProtocolDTO> = transaction {
        Protocols.selectAll().map(::rowToDto)
    }

    fun create(dto: ProtocolDTO): ProtocolDTO = transaction {
        val id = Protocols.insertAndGetId {
            it[title]          = dto.title
            it[species]        = dto.species
            it[taxonId]        = dto.taxonId
            it[explantType]    = dto.explantType
            it[mediumId]       = dto.mediumId
            it[photoperiodH]   = dto.photoperiodH.toBigDecimal()
            it[temperature]    = dto.temperature.toBigDecimal()
            it[lightIntensity] = dto.lightIntensity
            it[subcultureDays] = dto.subcultureDays
            it[successJson]    = dto.successJson ?: "{}"
            it[createdAt]      = DateTime.now()
        }.value
        getById(id)!!
    }

    fun getById(id: Int): ProtocolDTO? = transaction {
        Protocols.select { Protocols.id eq id }
                 .map(::rowToDto)
                 .singleOrNull()
    }

    private fun rowToDto(r: ResultRow) = ProtocolDTO(
        id             = r[Protocols.id].value,
        title          = r[Protocols.title],
        species        = r[Protocols.species],
        taxonId        = r[Protocols.taxonId],
        explantType    = r[Protocols.explantType],
        mediumId       = r[Protocols.mediumId].value,
        photoperiodH   = r[Protocols.photoperiodH].toDouble(),
        temperature    = r[Protocols.temperature].toDouble(),
        lightIntensity = r[Protocols.lightIntensity],
        subcultureDays = r[Protocols.subcultureDays],
        successJson    = r[Protocols.successJson],
        createdAt      = r[Protocols.createdAt].toString()
    )
}
