package com.josue.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Rectangle

class Level {
    val plataformas = arrayListOf(
        Rectangle(0f, 0f, 800f, 50f),
        Rectangle(200f, 150f, 100f, 20f),
        Rectangle(400f, 250f, 100f, 20f),
        Rectangle(600f, 350f, 150f, 20f),
        Rectangle(800f, 100f, 50f, 200f)
    )
    val monedas = arrayListOf(
        Rectangle(220f, 180f, 32f, 32f),
        Rectangle(430f, 280f, 32f, 32f),
        Rectangle(650f, 380f, 32f, 32f)
    )
    val enemigos = arrayListOf(Rectangle(500f, 50f, 32f, 32f))

    private val fondo = loadTexture("imagenes/fondos/fondo_bosque.png")
    private val pasto = loadTexture("imagenes/tiles/tile_pasto.png")
    private val tierra = loadTexture("imagenes/tiles/tile_tierra.png")
    private val moneda = loadTexture("imagenes/items/moneda.png")
    private val enemigo = loadTexture("imagenes/enemigos/enemigo_slime.png")

    private fun loadTexture(path: String): Texture? = try {
        val file = Gdx.files.internal(path)
        if (file.exists()) Texture(file) else null
    } catch (_: Exception) {
        null
    }

    fun draw(shapeRenderer: ShapeRenderer, batch: SpriteBatch) {
        batch.begin()
        fondo?.let { batch.draw(it, 0f, 0f, 800f, 480f) }
        if (pasto != null && tierra != null) {
            for (p in plataformas) {
                val tile = 32f
                var x = p.x
                while (x < p.x + p.width) {
                    val w = minOf(tile, p.x + p.width - x)
                    batch.draw(pasto, x, p.y + p.height - minOf(tile, p.height), w, minOf(tile, p.height))
                    val lowerHeight = p.height - minOf(tile, p.height)
                    if (lowerHeight > 0f) batch.draw(tierra, x, p.y, w, lowerHeight)
                    x += tile
                }
            }
        }
        if (moneda != null) for (m in monedas) if (m.width > 0f) batch.draw(moneda, m.x, m.y, m.width, m.height)
        if (enemigo != null) for (e in enemigos) batch.draw(enemigo, e.x, e.y, e.width, e.height)
        batch.end()

        // Visible fallback geometry if optional tile textures are absent.
        if (pasto == null || tierra == null) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
            for (p in plataformas) {
                shapeRenderer.color = Color(0.4f, 0.2f, 0.1f, 1f)
                shapeRenderer.rect(p.x, p.y, p.width, p.height)
                shapeRenderer.color = Color(0.2f, 0.7f, 0.2f, 1f)
                shapeRenderer.rect(p.x, p.y + p.height - minOf(8f, p.height), p.width, minOf(8f, p.height))
            }
            shapeRenderer.end()
        }
    }

    fun dispose() {
        fondo?.dispose()
        pasto?.dispose()
        tierra?.dispose()
        moneda?.dispose()
        enemigo?.dispose()
    }
}
