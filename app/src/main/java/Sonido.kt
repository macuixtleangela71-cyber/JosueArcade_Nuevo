import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Sound

class Sonido {
    var sonidoSalto: Sound? = null
    var sonidoGolpe: Sound? = null
    var sonidoMoneda: Sound? = null

    fun cargar() {
        try {
            // Cargamos los archivos .ogg de la carpeta assets/sonidos/
            sonidoSalto = Gdx.audio.newSound(Gdx.files.internal("sonidos/sfx_jump.ogg"))
            sonidoGolpe = Gdx.audio.newSound(Gdx.files.internal("sonidos/sfx_hurt.ogg"))
            // Usamos el sonido de la moneda que acabas de subir
            sonidoMoneda = Gdx.audio.newSound(Gdx.files.internal("sonidos/sfx_coin.ogg"))
        } catch (e: Exception) {
            // Si por alguna razón falta un archivo, el juego no se cierra, solo avisa.
            println("Aviso: Faltan algunos archivos de audio.")
        }
    }

    fun reproducirSalto() {
        sonidoSalto?.play(0.5f)
    }

    fun reproducirMoneda() {
        sonidoMoneda?.play(0.5f)
    }

    fun reproducirGolpe() {
        sonidoGolpe?.play(0.5f)
    }
    
    fun dispose() {
        sonidoSalto?.dispose()
        sonidoGolpe?.dispose()
        sonidoMoneda?.dispose()
    }
}