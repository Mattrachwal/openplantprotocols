// repositories/FormulaIngredientRepository.kt
class FormulaIngredientRepository {
    fun addLink(dto: FormulaIngredientDTO): FormulaIngredientDTO = transaction {
        val id = FormulaIngredients.insertAndGetId {
            it[formula]     = dto.formulaId
            it[ingredient]  = dto.ingredientId
            it[amount]      = dto.amount.toBigDecimal()
            it[unit]        = dto.unit
        }.value
        dto.copy(id = id)
    }
    fun listForFormula(formulaId: Int) = transaction {
        (FormulaIngredients innerJoin Ingredients)
            .select { FormulaIngredients.formula eq formulaId }
            .map {
                mapOf(
                    "linkId"      to it[FormulaIngredients.id].value,
                    "ingredientId" to it[Ingredients.id].value,
                    "name"        to it[Ingredients.name],
                    "amount"      to it[FormulaIngredients.amount].toDouble(),
                    "unit"        to it[FormulaIngredients.unit]
                )
            }
    }
}
