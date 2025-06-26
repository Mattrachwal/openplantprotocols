// routes/IngredientRoutes.kt
fun Route.ingredientRoutes() {
    val repo = IngredientRepository()
    route("/api/ingredients") {
        get { call.respond(repo.getAll()) }
        post {
            val dto = call.receive<IngredientDTO>()
            call.respond(HttpStatusCode.Created, repo.create(dto))
        }
    }
}
