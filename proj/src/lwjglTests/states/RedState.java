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

        if (colorWheel.getRed() <= colorWheel.getBlue())
            return new BlueState(colorWheel);

        return this;
    }

    @Override
    public IColorState moveRight(int amount) {
        if (colorWheel.getBlue() > colorWheel.getGreen())
            colorWheel.decBlue(amount);
        else
            colorWheel.incGreen(amount);

        if (colorWheel.getRed() <= colorWheel.getGreen())
            return new GreenState(colorWheel);

        return this;
    }

    @Override
    public IColorState moveUp(int amount) {
        colorWheel.incRed(amount);
        return this;
    }

    @Override
    public IColorState moveDown(int amount) {
        colorWheel.decRed(amount);
        return this;
    }

    @Override
    public String toString() {
        return  "RedState["
                + colorWheel.getRed() + ", "
                + colorWheel.getGreen() + ", "
                + colorWheel.getBlue() + "]";
    }
}
