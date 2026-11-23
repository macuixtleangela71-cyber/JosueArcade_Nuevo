import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture

class GestorRecursos {
    val manager = AssetManager()

    fun obtenerTextura(nombre: String): Texture? {
        if (manager.isLoaded(nombre)) {
            return manager.get(nombre, Texture::class.java)
        }
        return null
    }

    fun dispose() {
        manager.dispose()
    }
}