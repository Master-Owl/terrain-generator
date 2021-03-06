package lwjglTests.states;

import lwjglTests.ColorWheel;

public interface IColorState {
    IColorState moveRight(int amount);
    IColorState moveLeft(int amount);
    IColorState moveUp(int amount);
    IColorState moveDown(int amount);
    IColorState lighten(int amount);
    IColorState darken(int amount);
    ColorWheel  getColorWheel();
}