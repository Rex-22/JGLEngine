package core.graphics.renderer;

import core.graphics.buffer.IndexBuffer;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.system.MemoryUtil.NULL;

public class BatchRenderer2D implements IRenderer2D {

    private static final int RENDERER_MAX_SPRITES = 60000;
    private static final int RENDERER_VERTEX_SIZE = VertexData.size;
    private static final int RENDERER_SPRITE_SIZE = RENDERER_VERTEX_SIZE * 4;
    private static final int RENDERER_BUFFER_SIZE = RENDERER_SPRITE_SIZE * RENDERER_MAX_SPRITES;
    private static final int RENDERER_INDICES_SIZE = RENDERER_MAX_SPRITES * 6;

    private final int SHADER_POSITION = 0;
    private final int SHADER_COLOUR = 1;

    private int vao;
    private int vbo;
    private IndexBuffer ibo;
    private FloatBuffer buffer;
    private int indexCount;

    public BatchRenderer2D() {
        vao = glGenVertexArrays();
        vbo = glGenBuffers();

        glBindVertexArray(vao);
        glBindBuffer(GL_ARRAY_BUFFER, vbo);

        //VERTICES
        glBufferData(GL_ARRAY_BUFFER, RENDERER_BUFFER_SIZE, GL_DYNAMIC_DRAW);
        glEnableVertexAttribArray(SHADER_POSITION);
        glEnableVertexAttribArray(SHADER_COLOUR);
        glVertexAttribPointer(SHADER_POSITION, 3, GL_FLOAT, false, RENDERER_VERTEX_SIZE, 0);
        glVertexAttribPointer(SHADER_COLOUR, 4, GL_FLOAT, false, RENDERER_VERTEX_SIZE, 3 * 4);

        glBindBuffer(GL_ARRAY_BUFFER, 0);


        //INDICES
        int[] indices = new int[RENDERER_INDICES_SIZE];
        int offset = 0;
        for (int i = 0; i < RENDERER_INDICES_SIZE; i += 6) {
            indices[i] = offset + 0;
            indices[i + 1] = offset + 1;
            indices[i + 2] = offset + 2;

            indices[i + 3] = offset + 2;
            indices[i + 4] = offset + 3;
            indices[i + 5] = offset + 0;

            offset += 4;
        }

        ibo = new IndexBuffer(indices);

        glBindVertexArray(0);
    }

    @Override
    public void begin() {
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        buffer = glMapBuffer(GL_ARRAY_BUFFER, GL_WRITE_ONLY).order(ByteOrder.nativeOrder()).asFloatBuffer();
    }

    @Override
    public void submit(Renderable2D renderable2D) {

        final Vector3f pos = renderable2D.getPosition();
        final float w = renderable2D.getSize().x;
        final float h = renderable2D.getSize().y;
        final Vector4f color = renderable2D.getColour();

        // 0,0
        buffer.put(pos.x).put(pos.y).put(pos.z);
        buffer.put(color.x).put(color.y).put(color.z).put(color.w);

        // 0,1
        buffer.put(pos.x).put(pos.y + h).put(pos.z);
        buffer.put(color.x).put(color.y).put(color.z).put(color.w);

        // 1,1
        buffer.put(pos.x + w).put(pos.y + h).put(pos.z);
        buffer.put(color.x).put(color.y).put(color.z).put(color.w);

        // 1,0
        buffer.put(pos.x + w).put(pos.y).put(pos.z);
        buffer.put(color.x).put(color.y).put(color.z).put(color.w);

        indexCount += 6;
    }

    @Override
    public void end() {
        glUnmapBuffer(GL_ARRAY_BUFFER);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    @Override
    public void flush() {
        glBindVertexArray(vao);
        ibo.bind();

        glDrawElements(GL_TRIANGLES, indexCount, GL_UNSIGNED_INT, 0);

        ibo.unbind();
        glBindVertexArray(0);

        indexCount = 0;
    }
}
