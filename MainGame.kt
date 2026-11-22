import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Intersector

class MainGame : ApplicationAdapter() {
    lateinit var camera: OrthographicCamera
    lateinit var shapeRenderer: ShapeRenderer
    lateinit var batch: SpriteBatch
    
    lateinit var josue: Josue
    lateinit var nivel: Level
    lateinit var ui: UI_Juego
    lateinit var particulas: GestorParticulas
    lateinit var guardado: Guardado
    lateinit var sonido: Sonido
    lateinit var inputManager: InputManager
    
    var estadoActual = GameData.ESTADO_JUGANDO
    var misMonedas = 0

    override fun create() {
        // 1. Configurar Cámara y Herramientas de dibujo
        camera = OrthographicCamera()
        camera.setToOrtho(false, 800f, 480f)
        shapeRenderer = ShapeRenderer()
        batch = SpriteBatch()
        
        // 2. Cargar Sistemas
        sonido = Sonido()
        sonido.cargar()
        
        guardado = Guardado()
        misMonedas = guardado.obtenerMonedas()
        
        // 3. Crear Entidades
        josue = Josue(100f, 100f)
        nivel = Level()
        ui = UI_Juego()
        particulas = GestorParticulas()
        
        // 4. Configurar Controles
        inputManager = InputManager(josue, particulas, ui, sonido, camera.viewportWidth)
        Gdx.input.inputProcessor = inputManager
    }

    override fun render() {
        // Limpiar pantalla
        Gdx.gl.glClearColor(0.5f, 0.7f, 1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        val dt = Gdx.graphics.deltaTime

        // Actualizar lógica si estamos jugando
        if (estadoActual == GameData.ESTADO_JUGANDO) {
            actualizarFisica(dt)
            particulas.actualizar(dt)
            ui.actualizar(dt)
        }

        // La cámara sigue a Josue
        camera.position.x = josue.bounds.x + 400f
        camera.update()
        shapeRenderer.projectionMatrix = camera.combined
        batch.projectionMatrix = camera.combined

        // --- DIBUJAR TODO ---
        
        // 1. Dibujar Nivel (Fondo, Suelo, Monedas, Enemigos)
        nivel.draw(shapeRenderer, batch)

        // 2. Dibujar a Josue con Animaciones
        val frameActual = josue.getCurrentFrame()
        val flipX = !josue.isFacingRight
        
        batch.begin()
        batch.draw(
            frameActual,
            josue.bounds.x - 15f, // Ajuste para centrar la imagen
            josue.bounds.y, 
            64f, // Ancho visual
            josue.bounds.height * 1.2f, // Alto visual
            0, 0, frameActual.texture.width, frameActual.texture.height,
            flipX, false
        )
        batch.end()

        // 3. Dibujar Partículas
        particulas.dibujar(shapeRenderer)

        // 4. Dibujar Interfaz (Textos)
        ui.dibujar(misMonedas)
    }

    fun actualizarFisica(dt: Float) {
        // Actualizar el estado de Josue (gravedad, tiempo de animación)
        josue.update(dt)

        // Colisiones Horizontales (Paredes)
        josue.bounds.x += josue.velocity.x * dt
        for (plataforma in nivel.plataformas) {
            if (Intersector.overlaps(josue.bounds, plataforma)) {
                if (josue.velocity.x > 0) josue.bounds.x = plataforma.x - josue.bounds.width
                else if (josue.velocity.x < 0) josue.bounds.x = plataforma.x + plataforma.width
                josue.velocity.x = 0f
            }
        }

        // Colisiones Verticales (Suelo y Techo)
        josue.velocity.y += GameData.GRAVEDAD * dt
        josue.bounds.y += josue.velocity.y * dt
        josue.isGrounded = false

        for (plataforma in nivel.plataformas) {
            if (Intersector.overlaps(josue.bounds, plataforma)) {
                if (josue.velocity.y <= 0) {
                    josue.bounds.y = plataforma.y + plataforma.height
                    josue.velocity.y = 0f
                    josue.isGrounded = true
                } else if (josue.velocity.y > 0) {
                    josue.bounds.y = plataforma.y - josue.bounds.height
                    josue.velocity.y = 0f
                }
            }
        }
        
        // Lógica de Monedas
        for (moneda in nivel.monedas) {
            if (moneda.width > 0f && Intersector.overlaps(josue.bounds, moneda)) {
                moneda.set(0f, -100f, 0f, 0f) // Desaparecer
                misMonedas++
                guardado.guardarMonedas(misMonedas)
                if (this::sonido.isInitialized) sonido.reproducirMoneda()
                ui.mostrarMensaje("+1 Moneda!")
            }
        }

        // Lógica de Enemigos (Colisión)
        for (enemigo in nivel.enemigos) {
            if (Intersector.overlaps(josue.bounds, enemigo)) {
                ui.mostrarMensaje("No mames! Te chocaste.")
                if (this::sonido.isInitialized) sonido.reproducirGolpe()
                // Reiniciar posición al chocar
                josue.bounds.setPosition(100f, 100f)
                josue.velocity.set(0f, 0f)
            }
        }
        
        // Si cae al vacío
        if (josue.bounds.y < -100f) {
            josue.bounds.setPosition(100f, 100f)
            josue.velocity.set(0f, 0f)
        }
    }

    override fun dispose() {
        // Limpieza de memoria
        shapeRenderer.dispose()
        batch.dispose()
        ui.dispose()
        sonido.dispose()
        if (this::josue.isInitialized) {
            // Si tuvieras que liberar texturas de Josue, sería aquí
        }
    }
}