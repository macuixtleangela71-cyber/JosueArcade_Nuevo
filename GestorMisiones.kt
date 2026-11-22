class GestorMisiones {
    var monedasRecolectadas = 0
    val metaMonedas = 5
    var nivelCompletado = false

    fun recolectarMoneda() {
        monedasRecolectadas++
        if (monedasRecolectadas >= metaMonedas) {
            nivelCompletado = true
        }
    }
}