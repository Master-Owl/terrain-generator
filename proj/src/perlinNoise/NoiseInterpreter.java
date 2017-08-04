package perlinNoise;

import java.awt.Color;

public class NoiseInterpreter {
		
	/**
	 * The function to get a gradient map between two colors.
	 * @param perlinNoise The noise to be interpreted.
	 * @param gradientStart The first color.
	 * @param gradientEnd The second color.
	 * @return A gradient image representing the noise map.
	 */
	public static Color[][] GetGradientMap(float[][] perlinNoise, Color gradientStart, Color gradientEnd){
		int width = perlinNoise.length;
		int height = perlinNoise[0].length;
		
		Color[][] image = new Color[width][height];
		
		for (int x = 0; x < width; ++x) {
			for (int y = 0; y < height; ++y) {
				image[x][y] = getGradientColor(gradientStart, gradientEnd, perlinNoise[x][y]);
			}
		}		
		return image;
	}
	
	/**
	 * The function to get a colored noise map for specific colors instead of a two-color gradient.
	 * @param perlinNoise The noise to be interpreted.
	 * @param colors The colors desired to be used
	 * @param cutoffs The cutoff values (0.0-1.0) where the color "boundaries" are. This should have (number of colors - 1) cutoff values.
	 * @return A colored image representing the noise map.
	 */
	public static Color[][] GetColorMap(float[][] perlinNoise, Color[] colors, float[] cutoffs){
		int width = perlinNoise.length;
		int height = perlinNoise[0].length;
		if (colors.length == 0) return null;
		
		Color[][] image = new Color[width][height];
		
		for (int x = 0; x < width; ++x) {
			for (int y = 0; y < height; ++y) {
				image[x][y] = getMapColor(colors, cutoffs, perlinNoise[x][y]);				
			}
		}
		
		return image;
	}
	
	private static Color getGradientColor(Color gradientStart, Color gradientEnd, float value) {
		float inverse = 1 - value;
		return new Color(
				(int)(gradientStart.getRed() * value + gradientEnd.getRed() * inverse),
				(int)(gradientStart.getGreen() * value + gradientEnd.getGreen() * inverse),
				(int)(gradientStart.getBlue() * value + gradientEnd.getBlue() * inverse));
	}
	
	private static Color getMapColor(Color[] colors, float[] cutoffs, float val) {
		for (int x = 0; x < cutoffs.length; ++x) {
			if (x == 0) {
				if (val < cutoffs[0] && val >= 0)
					return colors[x];
			}
			else if (x == cutoffs.length - 1) {
				if (val < 1.0f && val >= cutoffs[x])
					return colors[x];
			}
			else {
				if (val < cutoffs[x] && val >= cutoffs[x - 1])
					return colors[x];
			}
		}
		
		return colors[0];
	}
}
