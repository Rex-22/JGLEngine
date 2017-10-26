package core.graphics.buffer;

import core.util.BufferUtils;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.glBindBuffer;

public class IndexBuffer{

    private int bufferID;
    private int count;

    public IndexBuffer(int[] data) {
        this.count = data.length;

        bufferID = glGenBuffers();

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, bufferID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, BufferUtils.createIntBuffer(data), GL_STATIC_DRAW);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    public void bind(){
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, bufferID);
    }

    public void unbind(){
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    public int getCount() {
        return count;
    }
}
