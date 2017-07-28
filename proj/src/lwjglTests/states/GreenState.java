package lwjglTests.states;


public class GreenState extends ColorState {
    public GreenState() {
        super();
    }

    @Override
    public IColorState moveLeft(int amount) {
        colorWheel.incRed(amount);
        colorWheel.decBlue(amount);
        return determineColor(colorWheel.getHex());
    }

    @Override
    public IColorState moveRight(int amount) {
        colorWheel.incBlue(amount);
        colorWheel.decRed(amount);
        return determineColor(colorWheel.getHex());
    }
}
