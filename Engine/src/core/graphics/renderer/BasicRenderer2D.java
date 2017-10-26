package core.graphics.renderer;

import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;

public class BasicRenderer2D implements IRenderer2D{

    private List<SimpleSprite> renderQueue;

    public BasicRenderer2D() {
        renderQueue = new ArrayList();
    }

    @Override
    public void begin() {

    }

    @Override
    public void submit(Renderable2D renderable2D) {
        SimpleSprite sprite = (SimpleSprite) renderable2D;
        renderQueue.add(sprite);
    }

    @Override
    public void flush() {
        for (Iterator<SimpleSprite> it = renderQueue.iterator(); it.hasNext(); ) {
            SimpleSprite renderable = it.next();
            renderable.getVAO().bind();
            renderable.getIBO().bind();

            renderable.getShader().setUniformMat4f("ml_matrix", new Matrix4f().translate(renderable.getPosition()));
            glDrawElements(GL_TRIANGLES, renderable.getIBO().getCount(), GL_UNSIGNED_INT, 0);

            renderable.getIBO().unbind();
            renderable.getVAO().unbind();

            it.remove();
        }
    }

    @Override
    public void end() {

    }
}
