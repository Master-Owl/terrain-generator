package lwjglTests;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_SHIFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT_SHIFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;
import static org.lwjgl.opengl.GL11.glViewport;

public class DummyGame implements IGameLogic {
    private final Renderer renderer;
    private float red;
    private float green;
    private float blue;

    private enum Key {UP, DOWN, RIGHT, LEFT, SPACE, SHIFT, NONE}

    private Key key;

    public DummyGame() {
        renderer = new Renderer();
        key = Key.NONE;
    }

    @Override
    public void init() throws Exception {
        renderer.init();
    }

    @Override
    public void input(Window window) {
        if (window.keyIsPressed(GLFW_KEY_UP)) key = Key.UP;
        else if (window.keyIsPressed(GLFW_KEY_DOWN)) key = Key.DOWN;
        else if (window.keyIsPressed(GLFW_KEY_RIGHT)) key = Key.RIGHT;
        else if (window.keyIsPressed(GLFW_KEY_LEFT)) key = Key.LEFT;
        else if (window.keyIsPressed(GLFW_KEY_LEFT_SHIFT | GLFW_KEY_RIGHT_SHIFT)) key = Key.SHIFT;
        else if (window.keyIsPressed(GLFW_KEY_SPACE)) key = Key.SPACE;
        else key = Key.NONE;
    }

    @Override
    public void update(float interval) {
        switch(key){
            case DOWN:
                break;
            case UP:
                break;
            case RIGHT:
                break;
            case LEFT:
                break;
            case SPACE:
                break;
            case SHIFT:
                break;
            default:
                break;
        }

    }

    @Override
    public void render(Window window) {
        if (window.isResized()) {
            glViewport(0, 0, window.getWidth(), window.getHeight());
            window.setResized(false);
        }

        renderer.clear();
    }
}