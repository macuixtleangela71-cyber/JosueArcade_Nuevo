import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch

class UI_Juego {
    private val batch = SpriteBatch()
    private val font = BitmapFont() // La fuente por defecto de LibGDX
    
    // Variables para los mensajes flotantes (ej. "¡No mames!")
    private var mensajeActual = ""
    private var tiempoMensaje = 0f

    init {
        // Hacemos la fuente un poco más grande para que se lea en celular
        font.data.setScale(2f)
        font.color = Color.WHITE
    }

    // Esta función la llamaremos cuando pase algo importante
    fun mostrarMensaje(texto: String) {
        mensajeActual = texto
        tiempoMensaje = 2.0f // El mensaje durará 2 segundos en pantalla
    }

    fun actualizar(dt: Float) {
        // Si hay un mensaje activo, le restamos tiempo
        if (tiempoMensaje > 0f) {
            tiempoMensaje -= dt
        }
    }

    fun dibujar(monedas: Int) {
        batch.begin()

        // 1. Dibujar el contador de monedas arriba a la izquierda
        font.color = Color.YELLOW
        font.draw(batch, "Monedas: $monedas", 20f, 460f)

        // 2. Dibujar el texto del botón de la tienda (arriba a la derecha)
        font.color = Color.CYAN
        font.draw(batch, "[ TIENDA ]", 600f, 460f)

        // 3. Dibujar el mensaje flotante si está activo
        if (tiempoMensaje > 0f) {
            font.color = Color.MAGENTA
            // Lo dibujamos en el centro aproximado de la pantalla
            font.draw(batch, mensajeActual, 250f, 240f)
        }

        batch.end()
    }

    fun dispose() {
        batch.dispose()
        font.dispose()
    }
}