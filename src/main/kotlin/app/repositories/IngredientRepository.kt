// repositories/IngredientRepository.kt
class IngredientRepository {
    fun create(dto: IngredientDTO): IngredientDTO = transaction {
        val id = Ingredients.insertAndGetId {
            it[name] = dto.name
            it[description] = dto.description
        }.value
        dto.copy(id = id)
    }
    fun getAll(): List<IngredientDTO> = transaction {
        Ingredients.selectAll().map {
            IngredientDTO(it[Ingredients.id].value, it[Ingredients.name], it[Ingredients.description])
        }
    }
}
