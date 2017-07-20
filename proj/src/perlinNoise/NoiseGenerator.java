package perlinNoise;

import java.util.Random;

public class NoiseGenerator {
	/**
	 * NoiseGenerator constructor.
	 * @param seed The seed desired to be used for the random noise generation.
	 * @param width The desired width of the array.
	 * @param height The desired height of the array.
	 */
	public NoiseGenerator(int seed, int width, int height) {
		if (width <= 0) width = 100;
		if (height <= 0) height = 100;
		
		this.seed = seed;
		this.width = width;
		this.height = height;
		this.whiteNoise = generateWhiteNoise();
	}
	
	private int seed;
	private int width;
	private int height;
	private float[][] whiteNoise;
	
	/**
	 * The function to create the base noise array.
	 * @return An array filled with values between 0.0 and 1.0
	 */
	private float[][] generateWhiteNoise(){
		Random rand = new Random(seed);
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
		float[][] smoothNoise = new float[width][height];
		
		int period = 1 << octave; // 2^octave
		float frequency = 1 / period;
		
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
	 * @return A linear interpolation between num1 and num2.
	 */
	private float interpolate(float num1, float num2, float alpha) {
		return num1 * (1 - alpha) + alpha * num2;
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
		float[][][] smoothNoise = new float[octaveCount][width][height];
		float persistance = 0.5f;
		
		// Generate the smooth noise for each octave
		for (int i = 0; i < octaveCount; ++i) {
			smoothNoise[i] = generateSmoothNoise(octaveCount);
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
