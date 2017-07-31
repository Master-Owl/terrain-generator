package lwjglTests;


/**
 * Contains the game loop code. Delegates the input, update, and render methods to the
 * IGameLogic instance.
 */
public class GameEngine implements Runnable {
  public static final int TARGET_FPS = 75;
  public static final int TARGET_UPS = 30;
  private final IGameLogic gameLogic;
  private final Thread gameLoopThread;
  private final Timer timer;
  private final Window window;

  public GameEngine(String windowTitle, int width, int height, boolean vSync, IGameLogic gameLogic)
      throws Exception {
    gameLoopThread = new Thread(this, "GAME_LOOP_THREAD");
    this.gameLogic = gameLogic;
    window = new Window(windowTitle, width, height, vSync);
    timer = new Timer();
  }

  protected void init() throws Exception {
    window.init();
    timer.init();
    gameLogic.init();
  }

  // The game loop //
  protected void gameLoop(){
    float elapsedTime;
    float accumulator = 0f;
    float interval = 1f / TARGET_UPS;

    boolean running = true;
    while (running && !window.windowShouldClose()) {
      elapsedTime = timer.getElapsedTime();
      accumulator += elapsedTime;

      input();
      while (accumulator >= interval){
        update(interval);
        accumulator -= interval;
      }

      render();

      if (!window.isvSync())
        sync();
    }
  }

  public void start() {
    String osName = System.getProperty("os.name");
    if (osName.contains("Mac")) gameLoopThread.run();
    else                        gameLoopThread.start();
  }

  protected void input() {
    gameLogic.input(window);
  }

  protected void update(float interval) {
    gameLogic.update(interval);
  }

  protected void render() {
    gameLogic.render(window);
    window.update();
  }

  private void sync(){
    float loopSlot = 1f / TARGET_FPS;
    double endTime = timer.getLastLoopTime() + loopSlot;
    while (timer.getTime() < endTime){
      try{
        Thread.sleep(1);
      } catch (InterruptedException e) {}
    }
  }

  @Override
  public void run() {
    try{
      init();
      gameLoop();
    } catch (Exception e){
      e.printStackTrace();
    }
  }
}