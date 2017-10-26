package core.app;

import core.graphics.Window;
import org.joml.Vector2f;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallbackI;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPos;

public class Input {

    static boolean[] keys        = new boolean[65536];
    public static Vector2f mousePos    = new Vector2f();
    static boolean[] mouse       = new boolean[8];

    private static Window window;

    public static void init(Window window){
        if (window != null) Input.window = window;
    }

    /**Wrapper class for the keyboard*/
    public static class Keyboard implements GLFWKeyCallbackI {
        public static boolean isKeyDown(int keycode) {
            return keys[keycode];
        }

        // Keyboard
        public void invoke(long window, int key, int scancode, int action, int mods) {
            keys[key] = action != GLFW_RELEASE;
        }
    }
    /**Wrapper class for the mouse pos*/
    public static class MousePos extends GLFWCursorPosCallback {
        // Mouse position
        public void invoke(long window, double xpos, double ypos) {
            mousePos.x = (float) xpos;
            mousePos.y = (float) ypos;
        }

        /**
         * Set the position of the mouse
         */
        public static void setPos(Vector2f mousePos) {
            glfwSetCursorPos(window.getWindowCallback(), mousePos.x, mousePos.y);
        }
        /**
         * Set the position of the mouse
         */
        public static void setPos(double x, double y) {
            glfwSetCursorPos(window.getWindowCallback(), x, y);
        }
    }
    /**Wrapper class for the mouse press*/
    public static class MouseButton extends GLFWMouseButtonCallback {
        public static boolean isMouseDown(int mouseButton) {
            return mouse[mouseButton];
        }

        // Mouse button
        public void invoke(long window, int button, int action, int mods) {
            mouse[button] = action != GLFW_RELEASE;
        }
    }
}
