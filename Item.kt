enum class TipoItem { MONEDA, SKIN, POTENCIADOR }

class Item(
    val id: String,
    val nombre: String,
    val tipo: TipoItem,
    val precio: Int,
    val descripcion: String
) {
    var comprado = false
    var equipado = false
}