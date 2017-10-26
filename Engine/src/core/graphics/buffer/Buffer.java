package core.graphics.buffer;

import core.util.BufferUtils;

import static org.lwjgl.opengl.GL15.*;

public class Buffer{

    private int bufferID;
    private int componentCount;

    public Buffer(float[] data, int componentCount) {
        this.componentCount = componentCount;

        bufferID = glGenBuffers();

        glBindBuffer(GL_ARRAY_BUFFER, bufferID);
        glBufferData(GL_ARRAY_BUFFER, BufferUtils.createFloatBuffer(data), GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    public int getComponentCount() {
        return componentCount;
    }

    public void bind(){
        glBindBuffer(GL_ARRAY_BUFFER, bufferID);
    }

    public void unbind(){
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }
}
