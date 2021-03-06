package lwjglTests.states;


import java.util.TreeMap;

import lwjglTests.ColorWheel;

public class ColorState implements IColorState {
    protected ColorWheel colorWheel;

    public ColorState(ColorWheel colorWheel){
        this.colorWheel = colorWheel;
    }

    public ColorWheel getColorWheel() { return colorWheel; }

    protected ColorState determineColor(char[] hexColor){
        char[] redHex = new char[2];
        char[] greenHex = new char[2];
        char[] blueHex = new char[2];
        redHex[0]   = hexColor[0];
        redHex[1]   = hexColor[1];
        greenHex[0] = hexColor[2];
        greenHex[1] = hexColor[3];
        blueHex[0]  = hexColor[4];
        blueHex[1]  = hexColor[5];

        int redVal = colorWheel.getColorInt(redHex);
        int greenVal = colorWheel.getColorInt(greenHex);
        int blueVal = colorWheel.getColorInt(blueHex);

        boolean equalVals =
                redVal == greenVal  ||
                greenVal == blueVal ||
                blueVal == redVal;

        if (redVal > greenVal && redVal > blueVal)   return new RedState(colorWheel);
        if (greenVal > redVal && greenVal > blueVal) return new GreenState(colorWheel);
        if (blueVal > redVal && blueVal > redVal)    return new BlueState(colorWheel);

        if (equalVals) return this;

        TreeMap<Integer, ColorState> orderedColors = new TreeMap<>();
        orderedColors.put(redVal, new RedState(colorWheel));
        orderedColors.put(greenVal, new GreenState(colorWheel));
        orderedColors.put(blueVal, new BlueState(colorWheel));

        // This will return whichever state has the highest color value
        return orderedColors.lastEntry().getValue();
    }

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
        colorWheel.incRed(amount);
        colorWheel.incGreen(amount);
        colorWheel.incBlue(amount);
        return this;
    }

    @Override
    public IColorState darken(int amount) {
        colorWheel.decRed(amount);
        colorWheel.decGreen(amount);
        colorWheel.decBlue(amount);
        return this;
    }
}
