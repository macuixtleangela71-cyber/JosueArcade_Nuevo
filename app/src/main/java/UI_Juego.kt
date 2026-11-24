package com.josue.game

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch

class UI_Juego {
    private val batch = SpriteBatch()
    private val font = BitmapFont()
    private var mensajeActual = ""
    private var tiempoMensaje = 0f

    init {
        font.data.setScale(2f)
        font.color = Color.WHITE
    }

    fun mostrarMensaje(texto: String) {
        mensajeActual = texto
        tiempoMensaje = 2f
    }

    fun actualizar(dt: Float) {
        if (tiempoMensaje > 0f) tiempoMensaje -= dt
    }

    fun dibujar(monedas: Int) {
        batch.begin()
        font.color = Color.YELLOW
        font.draw(batch, "Monedas: $monedas", 20f, 460f)
        font.color = Color.CYAN
        font.draw(batch, "[ TIENDA ]", 600f, 460f)
        if (tiempoMensaje > 0f) {
            font.color = Color.MAGENTA
            font.draw(batch, mensajeActual, 250f, 240f)
        }
        batch.end()
    }

    fun dispose() {
        batch.dispose()
        font.dispose()
    }
}
