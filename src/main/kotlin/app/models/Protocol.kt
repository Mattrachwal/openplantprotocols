package app.models

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.jodatime.datetime
import org.joda.time.DateTime

/** Core protocol header – MIAPPE-inspired */
object Protocols : IntIdTable() {
    val title            = varchar("title", 255)
    val species          = varchar("species", 255)                // "Alocasia amazonica"
    val taxonId          = varchar("taxon_id", 50)                // NCBI TaxID
    val explantType      = varchar("explant_type", 100)           // "leaf segment"
    val mediumId         = reference("medium_id", MediaFormulas)  // FK
    val photoperiodH     = decimal("photoperiod_h", 4, 1)         // e.g. 16.0
    val temperature      = decimal("temperature_c", 4, 1)         // constant T
    val lightIntensity   = integer("light_intensity")             // µmol m-2 s-1
    val subcultureDays   = integer("subculture_days")             // 30
    val successJson      = text("success_json")                   // JSONB if you add pgjdbc…
    val createdAt        = datetime("created_at").clientDefault { DateTime.now() }
}

/** Lightweight DTO for API */
data class ProtocolDTO(
    val id: Int? = null,
    val title: String,
    val species: String,
    val taxonId: String,
    val explantType: String,
    val mediumId: Int,
    val photoperiodH: Double,
    val temperature: Double,
    val lightIntensity: Int,
    val subcultureDays: Int,
    val successJson: String? = null,
    val createdAt: String? = null
)
