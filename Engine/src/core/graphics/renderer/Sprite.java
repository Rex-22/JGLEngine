package core.graphics.renderer;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class Sprite extends Renderable2D {

    public Sprite(float x, float y, float width, float height, Vector4f colour) {
        super(new Vector3f(x, y, 0), new Vector2f(width, height), colour);
    }

}
