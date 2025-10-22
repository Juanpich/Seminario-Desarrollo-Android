package ar.edu.unicen.seminario.ui

enum class SortPolicy(val label: String, val column: String, val ascending: Boolean) {
    NAME("Nombre", "name", true),
    RATING("Rating", "-rating", false),
    RELEASED("Lanzamiento", "-released", false);

    companion object {
        fun fromLabel(label: String): SortPolicy? =
            values().find { it.label == label }
    }
}

