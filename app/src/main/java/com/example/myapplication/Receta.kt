data class Receta(
    val nombreReceta: String = "",
    val dificultad: String = "",
    val porciones: Int = 0,
    val tiempoPreparacion: Int = 0,
    val preparacion: String = "",
    val ingredientes: List<String> = emptyList(),
    val cantidadIngredientes: List<String> = emptyList(),
    val imagenUrl: String = ""
)