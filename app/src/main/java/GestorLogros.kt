package com.josue.game

class GestorLogros {
    private val logrosDesbloqueados = mutableSetOf<String>()
    val LOGRO_PRIMER_SALTO = "¡Primer salto! Ya eres todo un pro."
    val LOGRO_PRIMER_MONEDA = "¡Claro que sí, carnal! Tu primera moneda."

    fun desbloquear(logro: String) {
        if (logrosDesbloqueados.add(logro)) println("LOGRO: $logro")
    }
}
