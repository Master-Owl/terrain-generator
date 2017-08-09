package display;

import com.sun.j3d.utils.applet.MainFrame;

public class Display {

	public static void main(String[] args) {
		   System.out.println("Program Started");
		   System.setProperty("sun.awt.noerasebackground", "true");

		   Test3D t3d = new Test3D();
		   
		   t3d.interactions();
		   new MainFrame(t3d, 512, 512);
	}
	
	public Display() {}	

}
