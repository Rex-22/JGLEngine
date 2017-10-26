package core.graphics.renderer;

import core.graphics.buffer.Buffer;
import core.graphics.buffer.IndexBuffer;
import core.graphics.buffer.VertexArray;
import core.graphics.shader.Shader;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class SimpleSprite extends Renderable2D {

    protected IndexBuffer indexBuffer;
    protected VertexArray vertexArray;

    protected Shader shader;

    public SimpleSprite(float x, float y, float width, float height, Vector4f colour, Shader shader) {
        super(new Vector3f(x, y, 0), new Vector2f(width, height), colour);
        this.shader = shader;

        vertexArray = new VertexArray();
        indexBuffer = new IndexBuffer(new int[]{0, 1, 2, 2, 3, 0});

        float[] vertices = new float[]{
                0,      0,      0,
                0,      height, 0,
                width,  height, 0,
                width,  0,      0
        };

        float[] col = new float[]{
                colour.x, colour.y, colour.z, colour.w,
                colour.x, colour.y, colour.z, colour.w,
                colour.x, colour.y, colour.z, colour.w,
                colour.x, colour.y, colour.z, colour.w,
        };

        vertexArray.addBuffer(new Buffer(vertices, 3), 0);
        vertexArray.addBuffer(new Buffer(col, 4), 2);
    }

    public Shader getShader() {
        return shader;
    }

    public IndexBuffer getIBO() {
        return indexBuffer;
    }

    public VertexArray getVAO() {
        return vertexArray;
    }

}
