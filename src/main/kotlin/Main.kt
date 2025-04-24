import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Database

fun main() {
    Database.connect(
        url = "jdbc:postgresql://localhost:5432/openplantprotocols",
        driver = "org.postgresql.Driver",
        user = "openpp",
        password = "openpppass"
    )

    transaction {
        println("Connected to PostgreSQL!")
    }
}
