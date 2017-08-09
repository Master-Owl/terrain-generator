package display;

import java.awt.Container;

import javax.media.j3d.AmbientLight;
import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
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

import com.sun.j3d.utils.geometry.Cone;
import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.SimpleUniverse;

public class Display {

	public static void main(String[] args) {
		System.setProperty("sun.awt.noerasebackground", "true");		
		Display d = new Display();
		Test3D t3d = d.new Test3D();
		
		t3d.material();
	}
	
	public Display() {}
	
	private class Test3D {
		private SimpleUniverse universe;
		private BranchGroup group;
		
		public Test3D(){
			universe = new SimpleUniverse();
			group = new BranchGroup();
		}
		
		public void sphere(float size){
			group.removeAllChildren();
			
			Sphere sphere = new Sphere(size);
			group.addChild(sphere);
			Color3f light_1_color = new Color3f(0.5f, 1.0f, 0.1f);
			BoundingSphere bounds = 
					new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
			Vector3f light_1_direction = new Vector3f(4.0f, 6.0f, -12.0f);
			DirectionalLight light_1 = new DirectionalLight(light_1_color, light_1_direction);
			light_1.setInfluencingBounds(bounds);
			group.addChild(light_1);
			
			universe.getViewingPlatform().setNominalViewingTransform();
			universe.addBranchGraph(group);
		}
		
		public void axis3D(){
			group.removeAllChildren();
			
			for (float x = -1.0f; x < 1.0f; x += 0.1f){
				Sphere s = new Sphere(0.05f);
				TransformGroup tg = new TransformGroup();
				Transform3D transform = new Transform3D();
				Vector3f vect = new Vector3f(x, 0.0f, 0.0f); // from -1.0 to 1.0 on x axis
				
				transform.setTranslation(vect);
				tg.setTransform(transform);
				tg.addChild(s);
				group.addChild(tg);
			}
			
			for (float y = -1.0f; y < 1.0f; y += 0.1f){
				Cone c = new Cone(0.05f, 0.1f);
				TransformGroup tg = new TransformGroup();
				Transform3D transform = new Transform3D();
				Vector3f vect = new Vector3f(0.0f, y, 0.0f); // from -1.0 to 1.0 on y axis
				
				transform.setTranslation(vect);
				tg.setTransform(transform);
				tg.addChild(c);
				group.addChild(tg);
			}
			
			for (float z = -1.0f; z < 1.0f; z += 0.1f){
				Cylinder c = new Cylinder(0.05f, 0.1f);
				TransformGroup tg = new TransformGroup();
				Transform3D transform = new Transform3D();
				Vector3f vect = new Vector3f(0.0f, 0.0f, z); // from -1.0 to 1.0 on z axis
				
				transform.setTranslation(vect);
				tg.setTransform(transform);
				tg.addChild(c);
				group.addChild(tg);
			}
			
			Color3f light_1_color = new Color3f(0.1f, 1.4f, 0.7f);
			Vector3f light_1_direction = new Vector3f(4.0f, -7.0f, -12.0f);
			DirectionalLight dl = new DirectionalLight(light_1_color, light_1_direction);
			
			BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
			dl.setInfluencingBounds(bounds);
			
			group.addChild(dl);
			universe.getViewingPlatform().setNominalViewingTransform();
			universe.addBranchGraph(group);
		}
	
		public void material(){
			group.removeAllChildren();
			Color3f green = new Color3f(0.0f, 0.5f, 0.1f);
			Color3f black = new Color3f(0, 0, 0);
			Color3f white = new Color3f(1.0f, 1.0f, 1.0f);
			
			// Texture map
			TextureLoader loader = 
					new TextureLoader("C:\\Users\\trevorkh\\Pictures\\Saved Pictures\\creeper-256.png", "RGB", new Container());
			Texture texture = loader.getTexture();
			texture.setBoundaryModeS(Texture.WRAP);
			texture.setBoundaryModeT(Texture.WRAP);
			texture.setBoundaryColor(new Color4f(0.0f, 1.0f, 0.0f, 0.0f));
			
			// Texture attributes
			TextureAttributes attr = new TextureAttributes();
			Appearance ap = new Appearance();
			attr.setTextureMode(TextureAttributes.MODULATE);
			ap.setTexture(texture);
			ap.setTextureAttributes(attr);
			ap.setMaterial(new Material(green, black, white, black, 1.0f));
			
			// Create ball
			int primitiveFlags = Primitive.GENERATE_NORMALS + Primitive.GENERATE_TEXTURE_COORDS;
			Sphere s = new Sphere(0.6f, primitiveFlags, ap);
			group.addChild(s);
			
			// Create lights
			BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
			Vector3f light_direction = new Vector3f(4.0f, -7.0f, -12.0f);
			DirectionalLight dl = new DirectionalLight(white, light_direction);
			dl.setInfluencingBounds(bounds);
			group.addChild(dl);
			
			AmbientLight ambient = new AmbientLight(new Color3f(0.4f, 0.4f, 0.4f));
			ambient.setInfluencingBounds(bounds);
			group.addChild(ambient);
			
			universe.getViewingPlatform().setNominalViewingTransform();
			universe.addBranchGraph(group);
		}
	}

}
