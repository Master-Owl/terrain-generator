package lwjglTests.states;


import lwjglTests.ColorWheel;

public class ColorState implements IColorState {
    protected ColorWheel colorWheel;

    public ColorState(){
        colorWheel = new ColorWheel();
    }

    public ColorWheel getColorWheel() { return colorWheel; }

    @Override
    public IColorState moveLeft(int amount) {
        return this;
    }

    @Override
    public IColorState moveRight(int amount) {
        return this;
    }

    @Override
    public IColorState moveUp(int amount) {
        return this;
    }

    @Override
    public IColorState moveDown(int amount) {
        return this;
    }

    @Override
    public IColorState lighten(int amount) {
        return this;
    }

    @Override
    public IColorState darken(int amount) {
        return this;
    }
}
