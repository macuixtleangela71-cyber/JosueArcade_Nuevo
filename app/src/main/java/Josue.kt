package com.josue.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2

class Josue(x: Float, y: Float) {
    val bounds = Rectangle(x, y, GameData.ANCHO_JOSUE, GameData.ALTO_JOSUE)
    val velocity = Vector2()
    var isGrounded = false
    var isFacingRight = true
        private set

    private val idleTexture = Texture(Gdx.files.internal("imagenes/personajes/player_idle.png"))
    private val jumpTexture = Texture(Gdx.files.internal("imagenes/personajes/player_jump.png"))
    private val idleFrame = TextureRegion(idleTexture)
    private val jumpFrame = TextureRegion(jumpTexture)

    fun moveLeft() {
        velocity.x = -GameData.VELOCIDAD_CORRER
        isFacingRight = false
    }

    fun moveRight() {
        velocity.x = GameData.VELOCIDAD_CORRER
        isFacingRight = true
    }

    fun stopMoving() {
        velocity.x = 0f
    }

    fun jump() {
        if (isGrounded) {
            velocity.y = GameData.FUERZA_SALTO
            isGrounded = false
        }
    }

    fun update(delta: Float) {
        // Keep frame time bounded after pauses or slow devices.
        if (delta <= 0f) return
    }

    fun getCurrentFrame(): TextureRegion = if (isGrounded) idleFrame else jumpFrame

    fun dispose() {
        idleTexture.dispose()
        jumpTexture.dispose()
    }
}
