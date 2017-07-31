package lwjglTests.states;


import lwjglTests.ColorWheel;

public class GreenState extends ColorState {
    public GreenState(ColorWheel cw) {
        super(cw);
    }

    @Override
    public IColorState moveLeft(int amount) {
        if (colorWheel.getBlue() > colorWheel.getRed())
            colorWheel.decBlue(amount);
        else
            colorWheel.incRed(amount);

        if (colorWheel.getGreen() == colorWheel.getRed())
            return new RedState(colorWheel);

        return this;
    }

    @Override
    public IColorState moveRight(int amount) {
        if (colorWheel.getRed() > colorWheel.getBlue())
            colorWheel.decRed(amount);
        else
            colorWheel.incBlue(amount);

        if (colorWheel.getGreen() == colorWheel.getBlue())
            return new BlueState(colorWheel);

        return this;
    }
}
