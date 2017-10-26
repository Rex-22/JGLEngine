package core.graphics.renderer;


import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class Renderable2D {

    protected Vector3f position;
    protected Vector2f size;
    protected Vector4f colour;

    public Renderable2D(Vector3f position, Vector2f size, Vector4f colour) {
        this.position = position;
        this.size = size;
        this.colour = colour;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector4f getColour() {
        return colour;
    }

    public Vector2f getSize() {
        return size;
    }
}

