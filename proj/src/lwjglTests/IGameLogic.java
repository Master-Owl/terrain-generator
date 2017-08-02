package lwjglTests;


/**
 * The interface that defines game logic functions
 */
public interface IGameLogic {

  void init() throws Exception;

  void input(Window window);

  void update(float interval);

  void render(Window window);
}
