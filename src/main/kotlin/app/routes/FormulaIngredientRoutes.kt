// routes/FormulaIngredientRoutes.kt
fun Route.formulaIngredientRoutes() {
    val repo = FormulaIngredientRepository()
    route("/api/formulas/{id}/ingredients") {
        get {
            val id = call.parameters["id"]!!.toInt()
            call.respond(repo.listForFormula(id))
        }
        post {
            val formulaId = call.parameters["id"]!!.toInt()
            val body = call.receive<FormulaIngredientDTO>()
            call.respond(HttpStatusCode.Created,
                repo.addLink(body.copy(formulaId = formulaId)))
        }
    }
}
