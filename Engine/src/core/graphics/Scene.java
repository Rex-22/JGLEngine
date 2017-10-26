package core.graphics;

public interface Scene {

    void init();

    void render();

    void update(float delta);

    void setWindow(Window window);
}
