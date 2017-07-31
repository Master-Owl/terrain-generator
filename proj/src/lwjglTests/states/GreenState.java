package lwjglTests.states;


import lwjglTests.ColorWheel;

public class GreenState extends ColorState {
    public GreenState(ColorWheel cw) {
        super(cw);
    }

    @Override
    public IColorState moveLeft(int amount) {
        if (colorWheel.getGreen() < colorWheel.getBlue() &&
            colorWheel.getGreen() < colorWheel.getRed())
            return new RedState(colorWheel);

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
        if (colorWheel.getGreen() < colorWheel.getBlue() &&
            colorWheel.getGreen() < colorWheel.getRed())
            return new BlueState(colorWheel);

        if (colorWheel.getRed() > colorWheel.getBlue())
            colorWheel.decRed(amount);
        else
            colorWheel.incBlue(amount);

        if (colorWheel.getGreen() == colorWheel.getBlue())
            return new BlueState(colorWheel);

        return this;
    }

    @Override
    public IColorState moveUp(int amount) {
        colorWheel.incGreen(amount);
        return this;
    }

    @Override
    public IColorState moveDown(int amount) {
        colorWheel.decGreen(amount);
        return this;
    }
}
