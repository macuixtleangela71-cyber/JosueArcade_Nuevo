package com.josue.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Sound

class Sonido {
    private var sonidoSalto: Sound? = null
    private var sonidoGolpe: Sound? = null
    private var sonidoMoneda: Sound? = null

    fun cargar() {
        sonidoSalto = cargarSonido("sonidos/sfx_jump.ogg")
        sonidoGolpe = cargarSonido("sonidos/sfx_hurt.ogg")
        sonidoMoneda = cargarSonido("sonidos/sfx_coin.ogg")
    }

    private fun cargarSonido(path: String): Sound? = try {
        val file = Gdx.files.internal(path)
        if (file.exists()) Gdx.audio.newSound(file) else null
    } catch (_: Exception) {
        null
    }

    fun reproducirSalto() { sonidoSalto?.play(0.5f) }
    fun reproducirMoneda() { sonidoMoneda?.play(0.5f) }
    fun reproducirGolpe() { sonidoGolpe?.play(0.5f) }

    fun dispose() {
        sonidoSalto?.dispose()
        sonidoGolpe?.dispose()
        sonidoMoneda?.dispose()
    }
}
