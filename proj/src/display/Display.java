package display;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GraphicsConfiguration;
import java.awt.Label;

import javax.media.j3d.AmbientLight;
import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Material;
import javax.media.j3d.Texture;
import javax.media.j3d.TextureAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Color4f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.geometry.Cone;
import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.SimpleUniverse;

public class Display {

	public static void main(String[] args) {
		   System.out.println("Program Started");

		   //System.setProperty("sun.awt.noerasebackground", "true");

		   Test3D t3d = new Test3D();
		   
		   t3d.interactions();
		   
//		   t3d.setupAnimation();
//		   t3d.addKeyListener(t3d);
//		   
		   MainFrame mf = new MainFrame(t3d, 512, 512); 
//           System.setProperty("sun.awt.noerasebackground", "true");
//           Rotate object = new Rotate();		 
//           object.frame = new MainFrame(object, args, object.imageWidth, object.imageHeight);
//           object.validate();
	}
	
	public Display() {}
	

}
