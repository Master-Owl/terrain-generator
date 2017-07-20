package perlinNoise;

import java.awt.Color;

public class NoiseInterpreter {
		
	public static Color[][] getGradientMap(float[][] perlinNoise, Color gradientStart, Color gradientEnd){
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
	
	private static Color getGradientColor(Color gradientStart, Color gradientEnd, float value) {
		float inverse = 1 - value;
		Color c = new Color(
				gradientStart.getRed() * inverse + gradientEnd.getRed() * value,
				gradientStart.getGreen() * inverse + gradientEnd.getGreen() * value,
				gradientStart.getBlue() * inverse + gradientEnd.getBlue() * value);
		
		return c;
	}
}
