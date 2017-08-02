package lwjglTests;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import java.awt.Color;

import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MAJOR;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MINOR;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_CORE_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_FORWARD_COMPAT;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwGetKey;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetFramebufferSizeCallback;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * The class containing the window logic
 */
public class Window {
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

    // This will make the program use the highest OpenGL version possible
    // between 3.2 and 4.1. If those lines are not included, a Legacy version of OpenGL is used.
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
    glfwSetFramebufferSizeCallback(windowHandle, (window, width, height) -> {
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
    glClearColor(r / 255f, g / 255f, b / 255f, alpha);
  }

  public void setClearColor(int rgb){
    Color c = new Color(rgb);
    glClearColor(c.getRed() / 255f, c.getGreen() / 255f, c.getBlue() / 255f, c.getAlpha());
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