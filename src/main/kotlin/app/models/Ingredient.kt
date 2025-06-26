// models/Ingredient.kt
package app.models
import org.jetbrains.exposed.dao.id.IntIdTable

object Ingredients : IntIdTable() {
    val name        = varchar("name", 100).uniqueIndex()
    val description = text("description").nullable()
}

object FormulaIngredients : IntIdTable() {
    val formula     = reference("formula_id", MediaFormulas)
    val ingredient  = reference("ingredient_id", Ingredients)
    val amount      = decimal("amount", 10, 3)   // e.g. 30.000
    val unit        = varchar("unit", 20)        // "g/L", "mg/L"
}

data class IngredientDTO(
    val id: Int? = null,
    val name: String,
    val description: String? = null
)

data class FormulaIngredientDTO(
    val id: Int? = null,
    val formulaId: Int,
    val ingredientId: Int,
    val amount: Double,
    val unit: String
)
