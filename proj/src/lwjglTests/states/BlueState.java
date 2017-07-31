package lwjglTests.states;


import lwjglTests.ColorWheel;

public class BlueState extends ColorState {
    public BlueState(ColorWheel cw) {
        super(cw);
    }

    @Override
    public IColorState moveLeft(int amount) {
        if (colorWheel.getRed() > colorWheel.getGreen())
            colorWheel.decRed(amount);
        else
            colorWheel.incGreen(amount);

        if (colorWheel.getBlue() == colorWheel.getGreen())
            return new GreenState(colorWheel);

        return this;
    }

    @Override
    public IColorState moveRight(int amount) {
        if (colorWheel.getGreen() > colorWheel.getRed())
            colorWheel.decGreen(amount);
        else
            colorWheel.incRed(amount);

        if (colorWheel.getBlue() == colorWheel.getRed())
            return new RedState(colorWheel);

        return this;
    }
}
