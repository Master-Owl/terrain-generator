package lwjglTests;

/*
    https://lwjglgamedev.gitbooks.io/3d-game-development-with-lwjgl/content/chapter2/chapter2.html
 */

public class Main {

  public static void main(String[] args) {
    try{
      boolean vSync = true;
      IGameLogic logic = new DummyGame();
      GameEngine engine = new GameEngine("Game", 600, 480, vSync, logic);
      engine.start();
    } catch (Exception e){
      e.printStackTrace();
      System.exit(-1);
    }
  }
}
