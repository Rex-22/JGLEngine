package core.graphics.renderer;

public interface IRenderer2D {

    void begin();
    void submit(Renderable2D renderable2D);
    void flush();
    void end();

}
