package display;

import java.awt.Color;

import com.sun.j3d.utils.applet.MainFrame;

import perlinNoise.NoiseGenerator;

// Docs: http://java.sun.com/products/java-media/3D/forDevelopers/j3dapi/index.html 

public class Display {

	public static void main(String[] args) {
		System.out.println("Program Started");
		System.setProperty("sun.awt.noerasebackground", "true");

//		test3DAxis();
		plane();
	}
	
	private static void test3DAxis(){
		Test3D td = new Test3D();
		td.axis3D();
		
		new MainFrame(td, 500, 500);
	}
	
	private static void plane(){
		int size = 3 * (6);
		boolean useWireframe = true;
		NoiseGenerator gen = new NoiseGenerator(42, size, size);
		Color[][] colorMap = new Color[size][size];

		for (int y = 0; y < size; ++y)
			for (int x = 0; x < size; ++x)
				colorMap[y][x] = Color.red;
		
		PlaneDrawer pd = new PlaneDrawer(gen.generatePerlinNoise(8), colorMap);
		pd.initPlane(useWireframe);
		
		new MainFrame(pd, 1000, 1000);
	}
}
