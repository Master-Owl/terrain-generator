package perlinNoise;

public class Settings {	
	public Settings() {}
	public Settings(Settings copy) {
		this.arrWidth = copy.arrWidth;
		this.arrHeight = copy.arrHeight;
		this.seed = copy.seed;
		this.octaves = copy.octaves;
		this.persistance = copy.persistance;
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

	private int arrWidth;
	private int arrHeight;
	private long seed;
	private int octaves = 8;
	private float persistance = 0.5f;
}
