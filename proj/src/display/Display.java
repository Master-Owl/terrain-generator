package display;

import java.awt.Dimension;
import java.util.Random;

import com.sun.j3d.utils.applet.MainFrame;

import perlinNoise.NoiseGenerator;

// Docs: http://java.sun.com/products/java-media/3D/forDevelopers/j3dapi/index.html 

public class Display {

	public static void main(String[] args) {
		System.out.println("Program Started");
		System.setProperty("sun.awt.noerasebackground", "true");

//		test3DAxis();
//		plane();
		application();
	}
	
	private static void test3DAxis(){
		Test3D td = new Test3D();
		td.axis3D();
		
		new MainFrame(td, 500, 500);
	}
	
	private static void plane(){
		int size = 25;
		float amplify = 25.0f;
		boolean useWireframe = true;
		Random rand = new Random();
		long seed = rand.nextLong();
		
		System.out.println("Seed: " + seed);
		NoiseGenerator gen = new NoiseGenerator(seed, size, size);
		
		PlaneDrawer pd = new PlaneDrawer(gen.generatePerlinNoise(8));
		pd.init(useWireframe, amplify, 7.0f);
		
		new MainFrame(pd, 1000, 1000);
	}
	
	private static void application(){
		GUI gui = new GUI("Terrain Visualization", new Dimension(1200, 1000));
		gui.init();
		gui.show();
	}
}
