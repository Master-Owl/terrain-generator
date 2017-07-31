package lwjglTests.states;

import lwjglTests.ColorWheel;

public class RedState extends ColorState {

    public RedState(ColorWheel cw){
        super(cw);
    }

    @Override
    public IColorState moveLeft(int amount) {
        if (colorWheel.getGreen() > colorWheel.getBlue())
            colorWheel.decGreen(amount);
        else
            colorWheel.incBlue(amount);

        if (colorWheel.getRed() == colorWheel.getBlue())
            return new BlueState(colorWheel);

        return this;
    }

    @Override
    public IColorState moveRight(int amount) {
        if (colorWheel.getBlue() > colorWheel.getGreen())
            colorWheel.decBlue(amount);
        else
            colorWheel.incGreen(amount);

        if (colorWheel.getRed() == colorWheel.getGreen())
            return new GreenState(colorWheel);

        return this;
    }
}
