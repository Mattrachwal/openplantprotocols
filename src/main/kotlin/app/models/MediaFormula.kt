package app.models

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.jodatime.datetime
import org.joda.time.DateTime

object MediaFormulas : IntIdTable() {
    val name = varchar("name", 255)
    val description = text("description")
    val createdAt = datetime("created_at")
}

data class MediaFormulaDTO(
    val id: Int? = null,
    val name: String,
    val description: String,
    val createdAt: String? = null
)
