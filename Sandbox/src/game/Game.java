package game;

import core.app.Application;
import core.app.Input;
import core.graphics.Scene;
import core.graphics.Window;
import core.graphics.renderer.BatchRenderer2D;
import core.graphics.renderer.IRenderer2D;
import core.graphics.renderer.Renderable2D;
import core.graphics.renderer.Sprite;
import core.graphics.shader.Shader;
import org.joml.*;
import org.joml.Math;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.lwjgl.opengl.GL11.GL_NO_ERROR;
import static org.lwjgl.opengl.GL11.glGetError;

public class Game implements Scene {

    private IRenderer2D renderer;

    private Shader shader;

    private List<Renderable2D> sprites;

    private Window window;

    public Game() {
    }

    @Override
    public void init() {

        Matrix4f ortho = new Matrix4f();
        ortho.ortho(0.0f, 16.0f, 0.0f, 9.0f, -1.0f, 1.0f);

        shader = new Shader("basic");

        sprites = new ArrayList<>();

        Random random = new Random();

        for (float y = 0; y < 9.0f; y += 0.05) {
            for (float x = 0; x < 16.0f; x += 0.05) {
                sprites.add(new Sprite(x, y, 0.04f, 0.04f, new Vector4f(random.nextInt() % 1000 / 1000.0f, 0, 1, 1)));
            }

        }

//        sprite1 = new Sprite(5, 5, 4, 4, new Vector4f(1, 0, 1, 1));
//        sprite2 = new Sprite(7, 1, 2, 3, new Vector4f(0.2f, 0, 1, 1));

        shader.enable();
        shader.setUniformMat4f("pr_matrix", ortho);

        renderer = new BatchRenderer2D();
    }

    float temp = 0.0f;

    @Override
    public void update(float delta) {
        double x = Input.mousePos.x;
        double y = Input.mousePos.y;

        temp += delta;

        shader.setUniform2f("light_pos", new Vector2f((float) (x * 16.0f / window.getWidth()), (float) (9.0f - y * 9.0f / window.getHeight())));

        shader.enable();
    }

    @Override
    public void render() {
        renderer.begin();

        for (Renderable2D sprite : sprites) {
            renderer.submit(sprite);
        }

        renderer.end();
        renderer.flush();
        int err;
        while ((err = glGetError()) != GL_NO_ERROR) {
            System.err.println("OpenGL error: " + err);
        }
    }

    @Override
    public void setWindow(Window window) {
        this.window = window;
    }

    public static void main(String args[]) {
        Application app = new Application(new Window("Sandbox", 960, 540), new Game());
        app.start();
    }


}
