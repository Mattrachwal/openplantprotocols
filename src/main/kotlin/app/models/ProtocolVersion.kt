// models/ProtocolVersion.kt
package app.models
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.jodatime.datetime
import org.joda.time.DateTime

object ProtocolVersions : IntIdTable() {
    val protocol   = reference("protocol_id", Protocols)
    val versionNum = integer("version_num")          // 1,2,3…
    val notes      = text("notes").nullable()
    val createdAt  = datetime("created_at").clientDefault { DateTime.now() }
}

object Steps : IntIdTable() {
    val version   = reference("version_id", ProtocolVersions)
    val stepNo    = integer("step_no")
    val instruction = text("instruction")
}
