package lwjglTests.states;


public class BlueState extends ColorState {
    public BlueState() {
        super();
    }

    @Override
    public IColorState moveLeft(int amount) {
        colorWheel.incGreen(amount);
        colorWheel.decRed(amount);
        return determineColor(colorWheel.getHex());
    }

    @Override
    public IColorState moveRight(int amount) {
        colorWheel.incRed(amount);
        colorWheel.decGreen(amount);
        return determineColor(colorWheel.getHex());
    }
}
