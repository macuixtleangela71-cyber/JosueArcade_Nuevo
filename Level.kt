import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Rectangle

class Level {
    val plataformas = ArrayList<Rectangle>()

    init {
        plataformas.add(Rectangle(0f, 0f, 800f, 50f))
        plataformas.add(Rectangle(200f, 150f, 100f, 20f))
        plataformas.add(Rectangle(400f, 250f, 100f, 20f))
        plataformas.add(Rectangle(600f, 350f, 150f, 20f))
        plataformas.add(Rectangle(800f, 100f, 50f, 200f))
    }

    fun draw(shapeRenderer: ShapeRenderer) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
        shapeRenderer.color = Color(0.4f, 0.2f, 0.1f, 1f)
        for (plataforma in plataformas) {
            shapeRenderer.rect(plataforma.x, plataforma.y, plataforma.width, plataforma.height)
        }
        shapeRenderer.end()
    }
}