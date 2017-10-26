package core.graphics;

import org.joml.Vector3f;
import org.joml.Vector4f;

public class Vertex {

    private Vector3f pos;
    private Vector4f colour;

    public Vertex(Vector3f pos, Vector4f colour) {
        this.pos = pos;
        this.colour = colour;
    }

}
