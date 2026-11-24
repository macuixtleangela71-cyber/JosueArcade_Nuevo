package com.josue.game

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Intersector

class MainGame : ApplicationAdapter() {
    private lateinit var camera: OrthographicCamera
    private lateinit var shapeRenderer: ShapeRenderer
    private lateinit var batch: SpriteBatch

    private lateinit var josue: Josue
    private lateinit var nivel: Level
    private lateinit var ui: UI_Juego
    private lateinit var particulas: GestorParticulas
    private lateinit var guardado: Guardado
    private lateinit var sonido: Sonido
    private lateinit var inputManager: InputManager

    private var estadoActual = GameData.ESTADO_JUGANDO
    private var misMonedas = 0

    override fun create() {
        camera = OrthographicCamera().apply { setToOrtho(false, 800f, 480f) }
        shapeRenderer = ShapeRenderer()
        batch = SpriteBatch()

        sonido = Sonido().also { it.cargar() }
        guardado = Guardado()
        misMonedas = guardado.obtenerMonedas()

        josue = Josue(100f, 100f)
        nivel = Level()
        ui = UI_Juego()
        particulas = GestorParticulas()

        inputManager = InputManager(josue, particulas, ui, sonido, camera.viewportWidth)
        Gdx.input.inputProcessor = inputManager
    }

    override fun render() {
        Gdx.gl.glClearColor(0.5f, 0.7f, 1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        val dt = Gdx.graphics.deltaTime
        if (estadoActual == GameData.ESTADO_JUGANDO) {
            actualizarFisica(dt)
            particulas.actualizar(dt)
            ui.actualizar(dt)
        }

        camera.position.x = josue.bounds.x + 400f
        camera.update()
        shapeRenderer.projectionMatrix = camera.combined
        batch.projectionMatrix = camera.combined

        nivel.draw(shapeRenderer, batch)
        val frameActual = josue.getCurrentFrame()
        batch.begin()
        batch.draw(
            frameActual,
            josue.bounds.x - 15f,
            josue.bounds.y,
            64f,
            josue.bounds.height * 1.2f,
            0,
            0,
            frameActual.texture.width,
            frameActual.texture.height,
            !josue.isFacingRight,
            false
        )
        batch.end()

        particulas.dibujar(shapeRenderer)
        ui.dibujar(misMonedas)
    }

    private fun actualizarFisica(dt: Float) {
        josue.update(dt)

        josue.bounds.x += josue.velocity.x * dt
        for (plataforma in nivel.plataformas) {
            if (Intersector.overlaps(josue.bounds, plataforma)) {
                if (josue.velocity.x > 0) josue.bounds.x = plataforma.x - josue.bounds.width
                else if (josue.velocity.x < 0) josue.bounds.x = plataforma.x + plataforma.width
                josue.velocity.x = 0f
            }
        }

        josue.velocity.y += GameData.GRAVEDAD * dt
        josue.bounds.y += josue.velocity.y * dt
        josue.isGrounded = false
        for (plataforma in nivel.plataformas) {
            if (Intersector.overlaps(josue.bounds, plataforma)) {
                if (josue.velocity.y <= 0) {
                    josue.bounds.y = plataforma.y + plataforma.height
                    josue.velocity.y = 0f
                    josue.isGrounded = true
                } else {
                    josue.bounds.y = plataforma.y - josue.bounds.height
                    josue.velocity.y = 0f
                }
            }
        }

        for (moneda in nivel.monedas) {
            if (moneda.width > 0f && Intersector.overlaps(josue.bounds, moneda)) {
                moneda.set(0f, -100f, 0f, 0f)
                misMonedas++
                guardado.guardarMonedas(misMonedas)
                sonido.reproducirMoneda()
                ui.mostrarMensaje("+1 Moneda!")
            }
        }

        for (enemigo in nivel.enemigos) {
            if (Intersector.overlaps(josue.bounds, enemigo)) {
                ui.mostrarMensaje("Te chocaste.")
                sonido.reproducirGolpe()
                josue.bounds.setPosition(100f, 100f)
                josue.velocity.set(0f, 0f)
            }
        }

        if (josue.bounds.y < -100f) {
            josue.bounds.setPosition(100f, 100f)
            josue.velocity.set(0f, 0f)
        }
    }

    override fun dispose() {
        if (this::nivel.isInitialized) nivel.dispose()
        if (this::josue.isInitialized) josue.dispose()
        if (this::ui.isInitialized) ui.dispose()
        if (this::sonido.isInitialized) sonido.dispose()
        if (this::shapeRenderer.isInitialized) shapeRenderer.dispose()
        if (this::batch.isInitialized) batch.dispose()
    }
}
