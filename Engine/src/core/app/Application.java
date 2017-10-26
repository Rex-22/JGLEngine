package core.app;

import core.graphics.Scene;
import core.graphics.Window;

public class Application {

    private Window window;
    private Scene scene;

    private boolean isRunning = false;

    public Application(Window window, Scene scene){
        this.window = window;
        this.scene = scene;
        this.scene.setWindow(window);
    }

    public void start() {
        scene.init();
        isRunning = true;

        long lastTime = System.nanoTime();
        double delta = 0.0;
        double ns = 1000000000.0 / 60.0;
        long timer = System.currentTimeMillis();
        int updates = 0;
        int frames = 0;

        while (isRunning){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            if (delta >= 1.0) {
                update((float)delta);
                updates++;
                delta--;
            }
            render();
            frames++;
            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println(updates + " ups, " + frames + " fps");
                updates = 0;
                frames = 0;
            }

            if (window.shouldClose())
                isRunning = false;
        }
    }

    public void render(){
        window.clear();

        scene.render();

        window.render();
    }

    public void update(float delta){
        scene.update(delta);

        window.update();
    }

}
