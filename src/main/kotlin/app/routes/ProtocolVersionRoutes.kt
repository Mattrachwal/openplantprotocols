// routes/ProtocolVersionRoutes.kt
fun Route.protocolVersionRoutes() {
    val repo = ProtocolVersionRepository() // implement similarly
    route("/api/protocols/{pid}/versions") {
        post { /* create new version + steps */ }
        get { /* list versions */ }
    }
}
