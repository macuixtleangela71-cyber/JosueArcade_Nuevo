package com.josue.game

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture

class GestorRecursos {
    val manager = AssetManager()
    fun obtenerTextura(nombre: String): Texture? =
        if (manager.isLoaded(nombre)) manager.get(nombre, Texture::class.java) else null
    fun dispose() { manager.dispose() }
}
