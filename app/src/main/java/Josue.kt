import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Rectangle

class Level {
    val plataformas = ArrayList<Rectangle>()
    val monedas = ArrayList<Rectangle>()
    val enemigos = ArrayList<Rectangle>()
    
    lateinit var texturaFondo: Texture
    lateinit var texturaPasto: Texture
    lateinit var texturaTierra: Texture
    lateinit var texturaMoneda: Texture
    lateinit var texturaEnemigo: Texture

    init {
        // El mapa
        plataformas.add(Rectangle(0f, 0f, 800f, 50f))
        plataformas.add(Rectangle(200f, 150f, 100f, 20f))
        plataformas.add(Rectangle(400f, 250f, 100f, 20f))
        plataformas.add(Rectangle(600f, 350f, 150f, 20f))
        
        // Monedas y Enemigos
        monedas.add(Rectangle(220f, 180f, 32f, 32f))
        monedas.add(Rectangle(430f, 280f, 32f, 32f))
        monedas.add(Rectangle(650f, 380f, 32f, 32f))
        enemigos.add(Rectangle(500f, 50f, 32f, 32f))
        
        // Cargar Texturas
        if (Gdx.files.internal("imagenes/fondos/fondo_bosque.png").exists()) texturaFondo = Texture(Gdx.files.internal("imagenes/fondos/fondo_bosque.png"))
        if (Gdx.files.internal("imagenes/tiles/tile_pasto.png").exists()) texturaPasto = Texture(Gdx.files.internal("imagenes/tiles/tile_pasto.png"))
        if (Gdx.files.internal("imagenes/tiles/tile_tierra.png").exists()) texturaTierra = Texture(Gdx.files.internal("imagenes/tiles/tile_tierra.png"))
        if (Gdx.files.internal("imagenes/items/moneda.png").exists()) texturaMoneda = Texture(Gdx.files.internal("imagenes/items/moneda.png"))
        if (Gdx.files.internal("imagenes/enemigos/enemigo_slime.png").exists()) texturaEnemigo = Texture(Gdx.files.internal("imagenes/enemigos/enemigo_slime.png"))
    }

    fun draw(shapeRenderer: ShapeRenderer, batch: SpriteBatch) {
        // 1. Fondo (Se repite o se estira)
        if (this::texturaFondo.isInitialized) {
            batch.begin()
            batch.draw(texturaFondo, 0f, 0f, 800f, 480f)
            batch.end()
        }

        // 2. Bloques del suelo (Pasto arriba, tierra abajo)
        if (this::texturaPasto.isInitialized && this::texturaTierra.isInitialized) {
            batch.begin()
            for (p in plataformas) {
                // Dibujamos el pasto en la superficie
                batch.draw(texturaPasto, p.x, p.y + p.height - 32f, 32f, 32f) // Un bloque de pasto
                // Dibujamos tierra debajo
                batch.draw(texturaTierra, p.x, p.y, 32f, p.height - 32f) 
            }
            batch.end()
        }

        // 3. Monedas y Enemigos
        batch.begin()
        if (this::texturaMoneda.isInitialized) {
            for (m in monedas) batch.draw(texturaMoneda, m.x, m.y, m.width, m.height)
        }
        if (this::texturaEnemigo.isInitialized) {
            for (e in enemigos) batch.draw(texturaEnemigo, e.x, e.y, e.width, e.height)
        }
        batch.end()
    }
}