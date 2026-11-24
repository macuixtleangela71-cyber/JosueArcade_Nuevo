package com.josue.game

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer

class Particula {
    var x = 0f
    var y = 0f
    var velX = 0f
    var velY = 0f
    var vida = 0f
    var activa = false
    var color = Color.WHITE

    fun actualizar(dt: Float) {
        if (!activa) return
        x += velX * dt
        y += velY * dt
        velY -= 500f * dt
        vida -= dt
        if (vida <= 0f) activa = false
    }
}

class GestorParticulas {
    private val pool = Array(100) { Particula() }

    fun emitirSalto(x: Float, y: Float) {
        for (p in pool) {
            if (!p.activa) {
                p.x = x
                p.y = y
                p.velX = (Math.random() * 100 - 50).toFloat()
                p.velY = (Math.random() * 100 + 50).toFloat()
                p.vida = 0.5f
                p.color = Color.GRAY
                p.activa = true
                break
            }
        }
    }

    fun actualizar(dt: Float) {
        for (p in pool) p.actualizar(dt)
    }

    fun dibujar(renderer: ShapeRenderer) {
        renderer.begin(ShapeRenderer.ShapeType.Filled)
        for (p in pool) if (p.activa) {
            renderer.color = p.color
            renderer.circle(p.x, p.y, 4f, 8)
        }
        renderer.end()
    }
}
