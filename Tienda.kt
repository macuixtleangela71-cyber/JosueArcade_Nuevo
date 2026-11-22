class Tienda(private val guardado: Guardado) {
    val catalogo = mutableListOf<Item>()

    init {
        // Llenamos la tienda con ítems
        catalogo.add(Item("skin_default", "Josue Clásico", TipoItem.SKIN, 0, "El héroe de siempre"))
        catalogo.add(Item("skin_neon", "Josue Neón", TipoItem.SKIN, GameData.PRECIO_SKIN_NEON, "Brilla en la oscuridad"))
        catalogo.add(Item("skin_dorada", "Josue de Oro", TipoItem.SKIN, GameData.PRECIO_SKIN_DORADA, "Para los verdaderos carnales"))
        catalogo.add(Item("pot_salto", "Doble Salto", TipoItem.POTENCIADOR, GameData.PRECIO_POTENCIADOR_SALTO, "Salta dos veces en el aire"))
    }

    fun intentarComprar(item: Item): Boolean {
        val monedasActuales = guardado.obtenerMonedas()
        
        if (item.comprado) return false // Ya lo tiene
        if (monedasActuales >= item.precio) {
            // Tiene dinero suficiente, ¡a comprar!
            guardado.guardarMonedas(monedasActuales - item.precio)
            guardado.desbloquearSkin(item.id) // Lo guardamos en la memoria
            item.comprado = true
            return true
        }
        return false // No tiene dinero
    }
}