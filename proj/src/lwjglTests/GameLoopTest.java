package lwjglTests;

/*
    https://lwjglgamedev.gitbooks.io/3d-game-development-with-lwjgl/content/chapter2/chapter2.html
 */

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*; // allows us to create windows
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.system.MemoryUtil.NULL;

public class GameLoopTest {

  /**
   * The class containing the window logic
   */
  private class Window {
    private boolean isResized;
    private boolean vSync;
    private int width;
    private int height;
    private String title;
    private long windowHandle;


    public Window(String title, int width, int height, boolean vSync){
      this.title = title;
      this.width = width;
      this.height = height;
      this.vSync = vSync;
      this.isResized = false;
    }

    public boolean keyIsPressed(int keyCode){
      return glfwGetKey(windowHandle, keyCode) == GLFW_PRESS;
    }

    public void init(){
      // Setup an error callback -- the default implementation
      // This will print the error message in System.err
      GLFWErrorCallback.createPrint(System.err).set();

      // Initialize GLFW. Most GLFW functions will not work before doing this.
      if (!glfwInit())
        throw new IllegalStateException("Unable to initialize GLFW");

      glfwWindowHint(GLFW_VISIBLE, GL_FALSE); // the window will stay hidden after creation
      glfwWindowHint(GLFW_RESIZABLE, GL_TRUE); // the window will be resizable
      glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
      glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
      glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
      glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);

      // Create the window and check for creation
      windowHandle = glfwCreateWindow(width, height, title, NULL, NULL);
      if (windowHandle == NULL)
        throw new RuntimeException("Failed to create GLFW window!");

      // Get resolution of the primary monitor
      GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

      // Center the window
      glfwSetWindowPos(
          windowHandle,
          (vidmode.width() - width) / 2,
          (vidmode.height() - height) / 2
      );

      // Make the OpenGL context current
      glfwMakeContextCurrent(windowHandle);

      // Setup a key callback. It will be called every time a key is pressed, repeated or released.
      glfwSetKeyCallback(windowHandle, (window, key, scancode, action, mods) -> {
        if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
          glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
        }
      });

      // Resize callback
      glfwSetFramebufferSizeCallback(windowHandle, (window, width, weight) -> {
        this.width = width;
        this.height = height;
        this.setResized(true);
      });

      // Make the OpenGL context current
      glfwMakeContextCurrent(windowHandle);

      if (isvSync()) {
        // Enable v-sync
        glfwSwapInterval(1);
      }

      // Make the window visible
      glfwShowWindow(windowHandle);

      GL.createCapabilities();

      // Set the clear color
      glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    }

    public void update() {
      glfwSwapBuffers(windowHandle);
      glfwPollEvents();
    }

    public void setClearColor(float r, float g, float b, float alpha) {
      glClearColor(r, g, b, alpha);
    }

    public boolean windowShouldClose() {
      return glfwWindowShouldClose(windowHandle);
    }

    public String getTitle() {
      return title;
    }

    public boolean isResized() {
      return isResized;
    }

    public void setResized(boolean resized) {
      isResized = resized;
    }

    public int getWidth() {
      return width;
    }

    public void setWidth(int width) {
      this.width = width;
    }

    public int getHeight() {
      return height;
    }

    public void setHeight(int height) {
      this.height = height;
    }

    public boolean isvSync(){
      return vSync;
    }
  }

  /**
   * The interface that defines game logic functions
   */
  private interface IGameLogic {

    void init() throws Exception;

    void input(Window window);

    void update(float interval);

    void render(Window window);
  }

  /**
   * The class containing rendering logic
   */
  private class Renderer {
    public void init() throws Exception {

    }

    public void clear(){
      glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }
  }

  /**
   * Contains the game loop code. Delegates the input, update, and render methods to the
   * IGameLogic instance.
   */
  private class GameEngine implements Runnable {
    public static final int TARGET_FPS = 75;
    public static final int TARGET_UPS = 30;
    private final Thread gameLoopThread;
    private final IGameLogic gameLogic;
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

  private class DummyGame implements IGameLogic {
    private final Renderer renderer;
    private int direction;
    private float color;

    public DummyGame(){
      renderer = new Renderer();
      direction = 0;
    }

    @Override
    public void init() throws Exception {
      renderer.init();
    }

    @Override
    public void input(Window window) {
      if (window.keyIsPressed(GLFW_KEY_UP)) direction = 1;
      else if (window.keyIsPressed(GLFW_KEY_DOWN)) direction = -1;
      else direction = 0;
    }

    @Override
    public void update(float interval) {
      color += direction * 0.01f;
      if (color > 1) color = 1.0f;
      else if (color < 0) color = 0.0f;

    }

    @Override
    public void render(Window window) {
      if (window.isResized()){
        glViewport(0, 0, window.getWidth(), window.getHeight());
        window.setResized(false);
      }
      window.setClearColor(color, color, color, 0.0f);
      renderer.clear();
    }
  }

  private class Timer {

    private double lastLoopTime;

    public void init() {
      lastLoopTime = getTime();
    }

    public double getTime() {
      return System.nanoTime() / 1000_000_000.0;
    }

    public float getElapsedTime() {
      double time = getTime();
      float elapsedTime = (float) (time - lastLoopTime);
      lastLoopTime = time;
      return elapsedTime;
    }

    public double getLastLoopTime() {
      return lastLoopTime;
    }
  }
}
