package lwjglTests.states;

public class RedState extends ColorState {

    public RedState(){
        super();
    }

    @Override
    public IColorState moveLeft(int amount) {
        colorWheel.incBlue(amount);
        colorWheel.decGreen(amount);
        return determineColor(colorWheel.getHex());
    }

    @Override
    public IColorState moveRight(int amount) {
        colorWheel.incGreen(amount);
        colorWheel.decBlue(amount);
        return determineColor(colorWheel.getHex());
    }
}
