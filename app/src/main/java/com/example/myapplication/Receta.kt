data class Receta(
    val nombreReceta: String = "",
    val dificultad: String = "",
    val porciones: Int = 0,
    val tiempoPreparacion: Int = 0,
    val preparacion: String = "",
    val ingredientes: List<String> = emptyList(),
    val imagenUrl: String = "",
    val recetaId: Int = 0,
    val calorias: Int = 0

)