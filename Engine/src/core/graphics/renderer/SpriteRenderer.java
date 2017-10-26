package core.graphics.renderer;

import static org.lwjgl.opengl.GL11.*;

public class SpriteRenderer implements IRenderer{

    @Override
    public void submit() {

    }

    @Override
    public void flush() {

    }

    public void clear() {
        glClear(GL_DEPTH_BUFFER_BIT | GL_COLOR_BUFFER_BIT);
        glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
    }
}
