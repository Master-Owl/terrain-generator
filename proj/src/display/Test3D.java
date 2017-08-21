package display;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Container;
import java.awt.GraphicsConfiguration;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.media.j3d.AmbientLight;
import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Material;
import javax.media.j3d.Node;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Texture;
import javax.media.j3d.Texture2D;
import javax.media.j3d.TextureAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.swing.Timer;
import javax.vecmath.Color3f;
import javax.vecmath.Color4f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Cone;
import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.SimpleUniverse;

@SuppressWarnings("serial")
public class Test3D extends Applet {
	private SimpleUniverse universe;
	private BranchGroup group;
	private Vector3f lightDir;
	BoundingSphere bounds;
	private Color3f green;
	private Color3f black;
	private Color3f white;
	private Color3f red;

	private TransformGroup objTrans;

	public Test3D() {
		lightDir = new Vector3f(4.0f, -7.0f, -12.0f);
		bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
		white = new Color3f(1.0f, 1.0f, 1.0f);
		black = new Color3f(0, 0, 0);
		green = new Color3f(0.0f, 0.65f, 0.1f);
		red = new Color3f(1.0f, 0.0f, 0.2f);

		objTrans = new TransformGroup();

		setLocation(500, 500);
	}

	public void sphere(float size) {
		group = new BranchGroup();
		universe = new SimpleUniverse();

		Sphere sphere = new Sphere(size);
		group.addChild(sphere);
		Color3f light_1_color = new Color3f(0.5f, 1.0f, 0.1f);
		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
		DirectionalLight light_1 = new DirectionalLight(light_1_color, lightDir);
		light_1.setInfluencingBounds(bounds);
		group.addChild(light_1);

		universe.getViewingPlatform().setNominalViewingTransform();
		universe.addBranchGraph(group);
	}

	public void axis3D() {
		setLayout(new BorderLayout());
		
		Canvas3D canvas = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
		add("Center", canvas);
		
		group = new BranchGroup();
		BranchGroup group2 = new BranchGroup();
		objTrans = new TransformGroup();
		universe = new SimpleUniverse(canvas);

		for (float x = -1.0f; x < 1.0f; x += 0.1f) {
			Sphere s = new Sphere(0.05f);
			TransformGroup tg = new TransformGroup();
			Transform3D transform = new Transform3D();
			Vector3f vect = new Vector3f(x, 0.0f, 0.0f); // from -1.0 to 1.0 on
															// x axis

			transform.setTranslation(vect);
			tg.setTransform(transform);
			tg.addChild(s);
			group2.addChild(tg);
		}

		for (float y = -1.0f; y < 1.0f; y += 0.1f) {
			Cone c = new Cone(0.05f, 0.1f);
			TransformGroup tg = new TransformGroup();
			Transform3D transform = new Transform3D();
			Vector3f vect = new Vector3f(0.0f, y, 0.0f); // from -1.0 to 1.0 on
															// y axis

			transform.setTranslation(vect);
			tg.setTransform(transform);
			tg.addChild(c);
			group2.addChild(tg);
		}

		for (float z = -1.0f; z < 1.0f; z += 0.1f) {
			Cylinder c = new Cylinder(0.05f, 0.1f);
			TransformGroup tg = new TransformGroup();
			Transform3D transform = new Transform3D();
			Vector3f vect = new Vector3f(0.0f, 0.0f, z); // from -1.0 to 1.0 on
															// z axis

			transform.setTranslation(vect);
			tg.setTransform(transform);
			tg.addChild(c);
			group2.addChild(tg);
		}

		OrbitBehavior behavior = new OrbitBehavior(canvas, OrbitBehavior.REVERSE_ROTATE);				
		DirectionalLight dl = new DirectionalLight(new Color3f(0.1f, 1.4f, 0.7f), lightDir);
		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
		
		behavior.setSchedulingBounds(bounds);
		behavior.setRotXFactor(1);
		behavior.setRotYFactor(1);
		
		dl.setInfluencingBounds(bounds);
		objTrans.addChild(group2);

		group.addChild(dl);
		group.addChild(objTrans);
		
		universe.getViewingPlatform().setViewPlatformBehavior(behavior);
		universe.getViewingPlatform().setNominalViewingTransform();
		universe.addBranchGraph(group);
	}

	public BranchGroup material() {
		group = new BranchGroup();

		// Texture map
		TextureLoader loader = new TextureLoader("C:\\Users\\trevorkh\\Pictures\\Saved Pictures\\creeper-256.png",
				"RGB", new Container());
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
		DirectionalLight dl = new DirectionalLight(white, lightDir);
		dl.setInfluencingBounds(bounds);
		group.addChild(dl);

		AmbientLight ambient = new AmbientLight(new Color3f(0.4f, 0.4f, 0.4f));
		ambient.setInfluencingBounds(bounds);
		group.addChild(ambient);

		return group;
	}

	public void useCanvas() {
		setLayout(new BorderLayout());
		GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
		Canvas3D canvas = new Canvas3D(config);
		add("North", new Label("TOP"));
		add("Center", canvas);
		add("South", new Label("BOTTOM"));

		universe = new SimpleUniverse(canvas);
		universe.getViewingPlatform().setNominalViewingTransform();
		universe.addBranchGraph(material());
	}
	// ----------------------------------------------

	// Rotation code snippet mostly taken from
	// http://www.java3d.org/miceandmen.html
	// OrbitBehavior taken from
	// https://stackoverflow.com/questions/17464571/rotating-a-3d-view-with-mouse-movements-with-a-fixed-camera
	public void interactions() {
		setLayout(new BorderLayout());

		GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
		Canvas3D canvas = new Canvas3D(config);
		add("Center", canvas);
		universe = new SimpleUniverse(canvas);
		group = new BranchGroup();

		TextureAttributes attr = new TextureAttributes();
		Appearance ap = getAppearance(new Color3f(Color.blue));
		attr.setTextureMode(TextureAttributes.MODULATE);
		ap.setTextureAttributes(attr);
		ap.setMaterial(new Material(green, black, green, white, 70f));
		Box box = new Box(0.5f, 0.5f, 0.5f, Primitive.GENERATE_TEXTURE_COORDS, ap);
		box.setCapability(Primitive.ENABLE_APPEARANCE_MODIFY);
		box.setCapability(Primitive.GEOMETRY_NOT_SHARED);
		box.setCapability(Node.ALLOW_LOCAL_TO_VWORLD_READ);

		Shape3D frontShape = box.getShape(Box.FRONT);
		frontShape.setAppearance(ap);

		box.getShape(Box.TOP).setAppearance(getAppearance(new Color3f(Color.magenta)));
		box.getShape(Box.BOTTOM).setAppearance(getAppearance(new Color3f(Color.orange)));
		box.getShape(Box.RIGHT).setAppearance(getAppearance(new Color3f(Color.red)));
		box.getShape(Box.LEFT).setAppearance(getAppearance(new Color3f(Color.green)));
		box.getShape(Box.BACK).setAppearance(getAppearance(new Color3f(Color.yellow)));

		OrbitBehavior behavior = new OrbitBehavior(canvas, OrbitBehavior.REVERSE_ROTATE);
		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
		objTrans = new TransformGroup();

		behavior.setSchedulingBounds(bounds);
		behavior.setRotXFactor(1);
		behavior.setRotYFactor(1);
		objTrans.addChild(box);
		group.addChild(objTrans);

		universe.getViewingPlatform().setViewPlatformBehavior(behavior);
		universe.getViewingPlatform().setNominalViewingTransform();
		universe.addBranchGraph(group);
	}

	public Appearance getAppearance(Color3f color) {
		Appearance ap = new Appearance();
		Texture texture = new Texture2D();
		TextureAttributes texAttr = new TextureAttributes();
		Material mat = new Material(color, black, color, white, 70f);
		ColoringAttributes ca = new ColoringAttributes(color, ColoringAttributes.NICEST);

		texAttr.setTextureMode(TextureAttributes.MODULATE);
		texture.setBoundaryModeS(Texture.WRAP);
		texture.setBoundaryModeT(Texture.WRAP);
		texture.setBoundaryColor(new Color4f(0.0f, 1.0f, 0.0f, 0.0f));

		ap.setTextureAttributes(texAttr);
		ap.setMaterial(mat);
		ap.setTexture(texture);
		ap.setColoringAttributes(ca);

		return ap;
	}
}
