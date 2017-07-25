package perlinNoise;

import java.awt.Color;

public class Settings {	
	public Settings() {}
	public Settings(Settings copy) {
		this.arrWidth = copy.arrWidth;
		this.arrHeight = copy.arrHeight;
		this.seed = copy.seed;
		this.octaves = copy.octaves;
		this.persistance = copy.persistance;
		this.gradientStart = copy.gradientStart;
		this.gradientEnd = copy.gradientEnd;
	}
	
	public int getArrWidth() {
		return arrWidth;
	}
	public void setArrWidth(int arrWidth) {
		this.arrWidth = arrWidth;
	}
	public int getArrHeight() {
		return arrHeight;
	}
	public void setArrHeight(int arrHeight) {
		this.arrHeight = arrHeight;
	}
	public long getSeed() {
		return seed;
	}
	public void setSeed(long seed) {
		this.seed = seed;
	}
	public int getOctaves() {
		return octaves;
	}
	public void setOctaves(int octaves) {
		this.octaves = octaves;
	}
	
	public float getPersistance() {
		return persistance;
	}

	public void setPersistance(float persistance) {
		this.persistance = persistance;
	}

	public Color getGradientStart() {
		return gradientStart;
	}
	public void setGradientStart(Color gradientStart) {
		this.gradientStart = gradientStart;
	}
	public Color getGradientEnd() {
		return gradientEnd;
	}
	public void setGradientEnd(Color gradientEnd) {
		this.gradientEnd = gradientEnd;
	}

	private int arrWidth;
	private int arrHeight;
	private long seed;
	private int octaves = 8;
	private float persistance = 0.5f;
	private Color gradientStart = Color.white;
	private Color gradientEnd = Color.black;
}
