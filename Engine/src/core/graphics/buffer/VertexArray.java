package core.graphics.buffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class VertexArray {

    public int vertexArrayID;

    public VertexArray() {
        vertexArrayID = glGenVertexArrays();
    }

    public void addBuffer(Buffer buffer, int index){
        bind();
        buffer.bind();

        glEnableVertexAttribArray(index);
        glVertexAttribPointer(index, buffer.getComponentCount(), GL_FLOAT, false, 0, 0);

        buffer.unbind();
        unbind();
    }

    public void bind(){
        glBindVertexArray(vertexArrayID);
    }

    public void unbind(){
        glBindVertexArray(0);
    }
}
