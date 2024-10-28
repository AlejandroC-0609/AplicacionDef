data class Receta(
    val nombre: String = "",
    val imagenUrl: String = "",
    val ingredientes: List<Map<String, String>> = emptyList(),
    val dificultad: String = "",
    val tiempo: String = "",
    val porciones: Int = 0,
    val preparacion: String = ""
)
