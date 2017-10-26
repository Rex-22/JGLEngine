package core.graphics;

import core.app.Input;
import org.lwjgl.glfw.GLFWFramebufferSizeCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {

    private long window;
    private int width;
    private int height;
    private String title;

    public Window(String title, int width, int height) {
        if (!glfwInit()){
            //TODO: Logger
            System.err.println("Failed to initialize GLFW!");
        }
        this.title = title;
        this.width = width;
        this.height = height;

        window = glfwCreateWindow(width, height, title, NULL, NULL);

        GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        int xp = (vidMode.width() - width) / 2;
        int yp = (vidMode.height() - height) / 2;
        glfwSetWindowPos(window, xp, yp);

        Input.init(this);
        glfwSetKeyCallback(window, new Input.Keyboard());
        glfwSetMouseButtonCallback(window, new Input.MouseButton());
        glfwSetCursorPosCallback(window, new Input.MousePos());

        glfwSetFramebufferSizeCallback(window,(long window, int w, int h)-> glViewport(0, 0, w, h));

        glfwSetWindowSizeCallback(window,(long window, int w, int h)-> {
            this.width = w;
            this.height = h;
        });

        glfwMakeContextCurrent(window);
        glfwShowWindow(window);

        glfwSwapInterval(0);

        GL.createCapabilities();
    }

    public void update(){
        glfwPollEvents();
    }

    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public void render(){
        glfwSwapBuffers(window);
    }

    public boolean shouldClose(){
        return glfwWindowShouldClose(window);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getTitle() {
        return title;
    }

    public long getWindowCallback() {
        return window;
    }
}
