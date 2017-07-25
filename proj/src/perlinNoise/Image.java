package perlinNoise;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Image {
	public static BufferedImage RenderImage(Color[][] img) {
		BufferedImage bufferedImage = new BufferedImage(img.length, img[0].length, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics2d = (Graphics2D)bufferedImage.getGraphics();
		for (int x = 0; x < img.length; ++x) {
			for (int y = 0; y < img[0].length; ++y) {
				graphics2d.setColor(img[x][y]);
				graphics2d.fillRect(x, y, 1, 1);
			}
		}
		
		return bufferedImage;
	}
}
