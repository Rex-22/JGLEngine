package core.graphics.renderer;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class VertexData {

    public static int vertex   = 3 * 4;
    public static int uv       = 2 * 4 + vertex;
    public static int colour   = 4 * 4 + uv;

    public static final int size = (4 * 3) + (4 * 2) + (4 * 4);

}
