package com.josue.game

class Tienda(private val guardado: Guardado) {
    val catalogo = mutableListOf(
        Item("skin_default", "Josue Clásico", TipoItem.SKIN, 0, "El héroe de siempre"),
        Item("skin_neon", "Josue Neón", TipoItem.SKIN, GameData.PRECIO_SKIN_NEON, "Brilla en la oscuridad"),
        Item("skin_dorada", "Josue de Oro", TipoItem.SKIN, GameData.PRECIO_SKIN_DORADA, "Para los verdaderos carnales"),
        Item("pot_salto", "Doble Salto", TipoItem.POTENCIADOR, GameData.PRECIO_POTENCIADOR_SALTO, "Salta dos veces en el aire")
    )

    fun intentarComprar(item: Item): Boolean {
        val monedasActuales = guardado.obtenerMonedas()
        if (item.comprado || monedasActuales < item.precio) return false
        guardado.guardarMonedas(monedasActuales - item.precio)
        guardado.desbloquearSkin(item.id)
        item.comprado = true
        return true
    }
}
