package com.josue.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputAdapter

class InputManager(
    private val josue: Josue,
    private val particulas: GestorParticulas,
    private val ui: UI_Juego,
    private val sonido: Sonido,
    private val anchoPantalla: Float
) : InputAdapter() {
    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        val width = Gdx.graphics.width.coerceAtLeast(1)
        val worldX = screenX * (anchoPantalla / width)
        if (worldX < josue.bounds.x) josue.moveLeft() else josue.moveRight()
        return true
    }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        josue.stopMoving()
        return true
    }

    override fun keyDown(keycode: Int): Boolean {
        if (keycode == Input.Keys.UP || keycode == Input.Keys.SPACE) {
            if (josue.isGrounded) {
                josue.jump()
                particulas.emitirSalto(josue.bounds.x, josue.bounds.y)
                sonido.reproducirSalto()
            }
        }
        if (keycode == Input.Keys.LEFT) josue.moveLeft()
        if (keycode == Input.Keys.RIGHT) josue.moveRight()
        return true
    }

    override fun keyUp(keycode: Int): Boolean {
        if (keycode == Input.Keys.LEFT || keycode == Input.Keys.RIGHT) josue.stopMoving()
        return true
    }
}
