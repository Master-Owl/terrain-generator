package perlinNoise;

import java.awt.Dimension;
import java.util.Random;

public class NoiseGenerator {
	/**
	 * NoiseGenerator constructor.
	 * @param seed The seed desired to be used for the random noise generation.
	 * @param width The desired width of the array.
	 * @param height The desired height of the array.
	 */
	public NoiseGenerator(long seed, int width, int height) {
		width = width <= 0 ? width : 100;
		width = width > 500 ? width : 500;
		height = height <= 0 ? height: 100;
		height = height > 500 ? height : 500;
		
		settings = new Settings();		
		settings.setSeed(seed);
		settings.setArrWidth(width);
		settings.setArrHeight(height);
		this.whiteNoise = generateWhiteNoise();
	}
	
	/**
	 * Sets a new seed and updates the noise accordingly.
	 * @param seed The new seed to use.
	 */
	public void setSeed(long seed) {
		settings.setSeed(seed);
		whiteNoise = generateWhiteNoise();
	}
	
	/** 
	 * @return The current seed.
	 */
	public long getSeed() { return settings.getSeed(); }
	
	/**
	 * Sets new dimensions and updates the noise accordingly.
	 * @param width The new width to use.
	 * @param height The new height to use.
	 */
	public void setDimensions(int width, int height) {
		settings.setArrWidth(width);
		settings.setArrHeight(height);
		whiteNoise = generateWhiteNoise();
	}
	
	/**
	 * A shortcut to setDimensions(int, int).
	 * @param dimension The dimension object to use.
	 */
	public void setDimensions(Dimension dimension) {
		setDimensions(dimension.width, dimension.height);
	}
	
	public Dimension getDimensions() { return new Dimension(settings.getArrWidth(), settings.getArrHeight()); }

	public Settings getSettings() { return settings; }
	
	public void changeSettings(Settings s) {
		settings = s;
		whiteNoise = generateWhiteNoise();
	}
	
	private float[][] whiteNoise;
	private Settings settings;
	
	/**
	 * The function to create the base noise array.
	 * @return An array filled with values between 0.0 and 1.0
	 */
	private float[][] generateWhiteNoise(){
		int width = settings.getArrWidth();
		int height = settings.getArrHeight();
		Random rand = new Random(settings.getSeed());
		float[][] noise = new float[width][height];
		for (int x = 0; x < width; ++x) {
			for (int y = 0; y < height; ++y) {
				noise[x][y] = rand.nextFloat();
			}
		}
		return noise;
	}
	
	/**
	 * The function to generate a new array for each repetition for blending purposes.
	 * @param octave The Kth iteration.
	 * @return An array of values blended to the given octave.
	 */
	private float[][] generateSmoothNoise(int octave){
		int width = settings.getArrWidth();
		int height = settings.getArrHeight();
		
		float[][] smoothNoise = new float[width][height];
		
		int period = 1 << octave; // 2^octave
		float frequency = 1.0f / period;
		
		for (int x = 0; x < width; ++x) {
			// Horizontal indices
			int ind1 = (x / period) * period; 	// Not the same as ind1 = x since int division floors the number
			int ind2 = (ind1 + period) % width; // Wrap by the width
			float horizontal_blend = (x - ind1) * frequency;
			
			for (int y = 0; y < height; ++y) {
				// Vertical indices
				int vert1 = (y / period) * period;
				int vert2 = (vert1 + period) % height; 
				float vertical_blend = (y - vert1) * frequency;
				
				// Blend top and bottom two corners
				float top 	 = interpolate(whiteNoise[ind1][vert1], whiteNoise[ind2][vert1], horizontal_blend);
				float bottom = interpolate(whiteNoise[ind1][vert2], whiteNoise[ind2][vert2], horizontal_blend);
				
				// Final blend
				smoothNoise[x][y] = interpolate(top, bottom, vertical_blend);
			}
		}
		
		return smoothNoise;
	}
	
	/**
	 * The closer alpha is to 0, the closer the resulting value will be to num1, 
	 * the closer alpha is to 1, the closer the resulting value will be to num2.
	 * For gradient purposes.
	 * @return A cosine interpolation between num1 and num2.
	 */
	private float interpolate(float num1, float num2, float alpha) {
		double angle = alpha * Math.PI;
		double prc = (1.0f - Math.cos(angle)) * 0.5f;
		return (float)(num1 * (1.0f - prc) + prc * num2);
	}
	
	/**
	 * The function to produce Perlin Noise.
	 * @param octaveCount The number of iterations to use.
	 * @return An array representing Perlin Noise.
	 */
	public float[][] generatePerlinNoise(int octaveCount){
		/*
		 * To make the final array, you add weighted values of all the smooth noise arrays together. The weight used for each octave is called the amplitude.
		 * Any values can be used for the amplitudes, with different effects. 
		 * A good starting point is to use a weight of 0.5 for the first octave, 0.25 for the next octave, and so on, multiplying the amplitude with 0.5 in each step.
		 * After you have added all the noise values, you should normalise it by dividing it by the sum of all the amplitudes, so that all noise values lie between 0 and 1.
		 */
		
		int width = settings.getArrWidth();
		int height = settings.getArrHeight();
		
		float[][][] smoothNoise = new float[octaveCount][width][height];
		float persistance = settings.getPersistance();
		
		// Generate the smooth noise for each octave
		for (int i = 0; i < octaveCount; ++i) {
			smoothNoise[i] = generateSmoothNoise(i);
		}
		
		float[][] perlinNoise = new float[width][height];
		float amplitude = 1.0f;
		float totalAmplitude = 0.0f;
		
		// Blend noise together
		for (int octave = octaveCount - 1; octave >= 0; --octave) {
			amplitude *= persistance;
			totalAmplitude += amplitude;
			
			for (int x = 0; x < width; ++x) {
				for (int y = 0; y < height; ++y) {
					perlinNoise[x][y] += smoothNoise[octave][x][y] * amplitude;
				}
			}
		}
		
		// Normalize by total amplitude
		for (int x = 0; x < width; ++x) {
			for (int y = 0; y < height; y++) {
				perlinNoise[x][y] /= totalAmplitude;
			}
		}
		
		return perlinNoise;
	}
}
