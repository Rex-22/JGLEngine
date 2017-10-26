package game;

import core.app.Application;
import core.app.Input;
import core.graphics.Scene;
import core.graphics.Window;
import core.graphics.renderer.SimpleSprite;
import core.graphics.renderer.Sprite;
import core.graphics.renderer.BasicRenderer2D;
import core.graphics.shader.Shader;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class Game implements Scene {

    private BasicRenderer2D renderer;

    private Shader shader;

    private SimpleSprite sprite1;
    private SimpleSprite sprite2;

    private Window window;

    public Game() {
    }

    @Override
    public void init() {

        Matrix4f ortho = new Matrix4f();
        ortho.ortho(0.0f, 16.0f, 0.0f, 9.0f, -1.0f, 1.0f);

        shader = new Shader("basic");

        renderer = new BasicRenderer2D();

        sprite1 = new SimpleSprite(5, 5, 5, 5, new Vector4f(1, 0, 1, 1), shader);
        sprite2 = new SimpleSprite(3, 2, 2, 2.5f, new Vector4f(0, 0, 1, 1), shader);


        shader.enable();
        shader.setUniformMat4f("pr_matrix", ortho);
    }

    @Override
    public void update(float delta) {
        double x = Input.mousePos.x;
        double y = Input.mousePos.y;
        shader.setUniform2f("light_pos", new Vector2f((float) (x * 16.0f / window.getWidth()), (float) (9.0f - y * 9.0f / window.getHeight())));
    }

    @Override
    public void render() {
        renderer.submit(sprite1);
        renderer.submit(sprite2);
        renderer.flush();
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
