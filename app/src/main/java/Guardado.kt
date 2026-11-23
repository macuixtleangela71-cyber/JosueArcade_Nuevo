import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Preferences

class Guardado {
    private val prefs: Preferences = Gdx.app.getPreferences("JosueGameData")

    fun guardarMonedas(cantidad: Int) {
        prefs.putInteger("monedas", cantidad)
        prefs.flush()
    }

    fun obtenerMonedas(): Int {
        return prefs.getInteger("monedas", 50)
    }

    fun desbloquearSkin(nombreSkin: String) {
        prefs.putBoolean("skin_$nombreSkin", true)
        prefs.flush()
    }

    fun tieneSkin(nombreSkin: String): Boolean {
        return prefs.getBoolean("skin_$nombreSkin", false)
    }
}