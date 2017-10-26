package core.graphics.renderer;

import core.graphics.buffer.IndexBuffer;
import core.graphics.buffer.VertexArray;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_READ_BUFFER;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.system.MemoryUtil.NULL;

public class BatchRenderer2D implements IRenderer2D {

    private static final int RENDERER_MAX_SPRITES   = 20000;
    private static final int RENDERER_VERTEX_SIZE   = VertexData.size;
    private static final int RENDERER_SPRITE_SIZE   = RENDERER_VERTEX_SIZE * 4;
    private static final int RENDERER_BUFFER_SIZE   = RENDERER_SPRITE_SIZE * RENDERER_MAX_SPRITES;
    private static final int RENDERER_INDICES_SIZE  = RENDERER_MAX_SPRITES * 6;

    private final int SHADER_POSITION = 0;
    private final int SHADER_UV = 1;
    private final int SHADER_COLOUR = 2;

    private int vao;
    private int vbo;
    private IndexBuffer ibo;
    private int indexCount;

    public BatchRenderer2D() {
        vbo = glGenBuffers();
        vao = glGenVertexArrays();

        glBindVertexArray(vao);
        glBindBuffer(GL_ARRAY_BUFFER, vbo);

        //VERTICES
        glBufferData(RENDERER_BUFFER_SIZE, NULL, GL_DYNAMIC_DRAW);
        glEnableVertexAttribArray(SHADER_POSITION);
        glEnableVertexAttribArray(SHADER_COLOUR);
        glVertexAttribPointer(SHADER_POSITION, 3, GL_FLOAT, false, RENDERER_VERTEX_SIZE, 0);
        glVertexAttribPointer(SHADER_COLOUR, 3, GL_FLOAT, false, RENDERER_VERTEX_SIZE, 3 * 4);
        glBindBuffer(GL_ARRAY_BUFFER, 0);


        //INDICES
        int[] indices = new int[RENDERER_INDICES_SIZE];
        int offset = 0;
        for (int i = 0; i < indices.length; i+= 6) {
            indices[  i  ] = offset + 0 ;
            indices[i + 1] = offset + 1 ;
            indices[i + 2] = offset + 2 ;

            indices[i + 3] = offset + 2 ;
            indices[i + 5] = offset + 3 ;
            indices[i + 6] = offset + 0 ;

            offset += 4;
        }

        ibo = new IndexBuffer(indices);

        glBindVertexArray(0);
    }

    @Override
    public void begin() {

    }

    @Override
    public void submit(Renderable2D renderable2D) {
        ByteBuffer buffer = glMapBuffer(GL_ARRAY_BUFFER, GL_WRITE_ONLY);

        //Vertex
        buffer.getFloat();
        buffer.getFloat();
        buffer.getFloat();

        //UV
        buffer.getFloat();
        buffer.getFloat();

        //Colour
        buffer.getFloat();
        buffer.getFloat();
        buffer.getFloat();
        buffer.getFloat();

    }

    @Override
    public void flush() {

    }

    @Override
    public void end() {

    }
}
