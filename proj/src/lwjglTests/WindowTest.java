package lwjglTests;

import static org.lwjgl.glfw.GLFW.*; // allows us to create windows
import static org.lwjgl.opengl.GL11.*; // gives us access to things like "GL_TRUE" which we'll need
import static org.lwjgl.system.MemoryUtil.*; // allows us to use 'NULL' in our code, note this is slightly different from java's 'null'

public class WindowTest implements Runnable {
  private Thread thread;
  private int width;
  private int height;
  private long glfwWindow;
  public boolean running = true;

  public static void main(String[] args) {
    WindowTest windowTest = new WindowTest();
    windowTest.start();
  }

  public void start() {
    running = true;
    thread = new Thread(this, "WindowTest");
    thread.start();
  }

  public void init(){
    if (!glfwInit()){
      System.err.println("GLFW init failed!");
    }

    // Resizable glfwWindow
    glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);

    // Create glfwWindow
    glfwWindow = glfwCreateWindow(300, 300, "Window Test", NULL, NULL);

    if (glfwWindow == NULL){
      System.err.println("Window couldn't be created!");
    }

    glfwSetWindowPos(glfwWindow, 1000, 500);

    // Sets context of GLFW
    glfwMakeContextCurrent(glfwWindow);

    // Sets glfwWindow visible
    glfwShowWindow(glfwWindow);
  }

  public void update(){
    // Polls for glfwWindow events
    glfwPollEvents();
  }

  public void render(){
    glfwSwapBuffers(glfwWindow);
  }

  @Override
  public void run() {
    init();
    while (running){
      update();
      render();
      if (glfwWindowShouldClose(glfwWindow))
        running = false;
    }
  }
}
