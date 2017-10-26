package game;

import core.app.Application;
import core.app.Input;
import core.graphics.Scene;
import core.graphics.Window;
import core.graphics.buffer.Buffer;
import core.graphics.buffer.IndexBuffer;
import core.graphics.buffer.VertexArray;
import core.graphics.shader.Shader;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import static org.lwjgl.opengl.GL11.*;

public class Game implements Scene {

    private VertexArray vao;
    private Buffer vbo;
    private Buffer colourBuffer;
    private IndexBuffer ibo;

    private Shader shader;

    private Window window;

    public Game() {}

    @Override
    public void init() {

        vbo = new Buffer(new float[]{
                0, 0, 0,
                0, 3, 0,
                8, 3, 0,
                8, 0, 0
        }, 3);

        colourBuffer = new Buffer(new float[]{
                1.0f, 0.0f, 1.0f, 1.0f,
                1.0f, 0.0f, 1.0f, 1.0f,
                1.0f, 0.0f, 1.0f, 1.0f,
                1.0f, 0.0f, 1.0f, 1.0f,
        }, 4);

        ibo = new IndexBuffer(new int[]
                {0, 1, 2,
                2, 3, 0});

        vao = new VertexArray();
        vao.addBuffer(vbo, 0);
        vao.addBuffer(colourBuffer, 1);

        Matrix4f ortho = new Matrix4f();
        ortho.ortho(0.0f, 16.0f, 0.0f, 9.0f, -1.0f, 1.0f);

        shader = new Shader("basic");
        shader.enable();
        shader.setUniformMat4f("pr_matrix", ortho);
        shader.setUniformMat4f("ml_matrix", new Matrix4f().translate(new Vector3f(4, 3, 0)));

        shader.setUniform2f("light_pos", new Vector2f(4.0f, 1.5f));
    }

    @Override
    public void render() {
        vao.bind();
        ibo.bind();

        glDrawElements(GL_TRIANGLES, ibo.getCount(), GL_UNSIGNED_INT, 0);

        ibo.unbind();
        vao.unbind();
    }

    @Override
    public void update(float delta) {
        double x = Input.mousePos.x;
        double y = Input.mousePos.y;
        shader.setUniform2f("light_pos", new Vector2f((float)(x * 16.0f / window.getWidth()), (float)(9.0f - y * 9.0f / window.getHeight())));
    }

    @Override
    public void setWindow(Window window) {
        this.window = window;
    }

    public static void main(String args[]){
        Application app = new Application(new Window("Sandbox", 960, 540), new Game());
        app.start();
    }



}
