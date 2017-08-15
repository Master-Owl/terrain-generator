package display;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GraphicsConfiguration;

import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.LineStripArray;
import javax.media.j3d.Material;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.QuadArray;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.geometry.Cone;
import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.universe.SimpleUniverse;

public class PlaneDrawer extends Applet {
	private float[][] noiseMap;
	private Color[][] colorMap;
	private int width;
	private int height;
	private float midY;

	public PlaneDrawer(float[][] noiseMap) {
		this.noiseMap = noiseMap;
		height = noiseMap.length;
		width = noiseMap[0].length;
		midY = 0.0f;
	}
	
	public void setNoiseMap(float[][] noiseMap){
		this.noiseMap = noiseMap;
	}

	public static Appearance createAppearance(boolean wireframe) {
		Appearance ap = new Appearance();

		if (wireframe) {
			// Render as wireframe
			PolygonAttributes polyAttr = new PolygonAttributes();
			polyAttr.setPolygonMode(PolygonAttributes.POLYGON_LINE);
			polyAttr.setCullFace(PolygonAttributes.CULL_NONE);
			ap.setPolygonAttributes(polyAttr);
		} else {
			Material mat = new Material(new Color3f(Color.green), new Color3f(Color.black), new Color3f(Color.white),
					new Color3f(Color.black), 1.0f);
			ap.setMaterial(mat);
		}

		return ap;
	}

	private LineStripArray[] getMesh(float scaleHeight, float scaleLengthWidth) {
		LineStripArray[] mesh = new LineStripArray[width + height];
		boolean sameMid = false;

		// Row lines
		for (int row = 0; row < height; ++row) {
			Point3f[] rowVertices = new Point3f[width];

			for (int col = 0; col < width; ++col) {
				Point3f vert = new Point3f();

				vert.x = col * scaleLengthWidth;
				vert.y = noiseMap[row][col] * scaleHeight;
				vert.z = row * scaleLengthWidth;

				rowVertices[col] = vert;

				if (row == height / 2 && col == width / 2)
					midY = vert.y;
			}

			mesh[row] = new LineStripArray(width, GeometryArray.COORDINATES, new int[] { width });
			mesh[row].setCoordinates(0, rowVertices);
		}

		// Column lines
		for (int col = 0; col < width; ++col) {
			Point3f[] colVertices = new Point3f[height];

			for (int row = 0; row < height; ++row) {
				Point3f vert = new Point3f();

				vert.x = col * scaleLengthWidth;
				vert.y = noiseMap[row][col] * scaleHeight;
				vert.z = row * scaleLengthWidth;

				colVertices[row] = vert;

				if (row == height / 2 && col == width / 2 && midY == vert.y)
					sameMid = true;
			}

			mesh[height + col] = new LineStripArray(height, GeometryArray.COORDINATES, new int[] { height });
			mesh[height + col].setCoordinates(0, colVertices);
		}

		System.out.println("Same Mid: " + sameMid);
		if (sameMid) System.out.println("   " + midY);

		return mesh;
	}

	public void init(boolean useWireframe, float amplify){
		removeAll();
		setLayout(new BorderLayout());		
		
		GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
		BranchGroup group = new BranchGroup();
		BranchGroup group2 = new BranchGroup();
		Canvas3D canvas = new Canvas3D(config);
		SimpleUniverse u = new SimpleUniverse(canvas);
		
		float scale = 7.0f / ((width + height) / 2.0f); // The width and length
														// will only go out to
														// x=7 z=7, dividing by
														// the avg of width &
														// height (should be sqr
														// anyway)
		
		add("Center", canvas);
		
		if (useWireframe) group2 = getAxisRef();
		initPlane(group2, amplify, scale);
		
		OrbitBehavior behavior = new OrbitBehavior(canvas, OrbitBehavior.REVERSE_ROTATE);
		DirectionalLight dl = new DirectionalLight(new Color3f(Color.cyan), new Vector3f(4.0f, -7.0f, 12.0f));
		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
		TransformGroup objTrans = new TransformGroup();
		Transform3D initialView = lookTowardsOriginFrom(new Point3d(0.0, 0.75, -10.0));
		
		dl.setInfluencingBounds(bounds);
		objTrans.addChild(group2);

		behavior.setSchedulingBounds(bounds);
		behavior.setRotXFactor(1);
		behavior.setRotYFactor(1);

		group.addChild(dl);
		group.addChild(objTrans);

		u.getViewingPlatform().setViewPlatformBehavior(behavior);
		u.getViewingPlatform().getViewPlatformTransform().setTransform(initialView);
		u.addBranchGraph(group);
	}
	
	public void initPlane(BranchGroup group, float amplify, float scale) {
		LineStripArray[] mesh = getMesh(amplify, scale);
		float RATIO = -midY;

		for (int i = 0; i < mesh.length; ++i) {
			Shape3D meshLine = new Shape3D(mesh[i], createAppearance(true));

			TransformGroup centerPlane = new TransformGroup();
			Transform3D centerTrans = new Transform3D();
			Vector3f centerVect = new Vector3f(-(width / height) * (width / 8.0f), RATIO, -(height / width) * (height / 8.0f));

			centerTrans.setTranslation(centerVect);
			centerPlane.setTransform(centerTrans);
			centerPlane.addChild(meshLine);

			group.addChild(centerPlane);
		}
	}

	private BranchGroup getAxisRef() {
		BranchGroup group = new BranchGroup();
		for (float x = -1.0f; x < 1.0f; x += 0.1f) {
			Sphere s = new Sphere(0.05f);
			TransformGroup tg = new TransformGroup();
			Transform3D transform = new Transform3D();
			Vector3f vect = new Vector3f(x, 0.0f, 0.0f); // from -1.0 to 1.0 on
															// x axis

			transform.setTranslation(vect);
			tg.setTransform(transform);
			tg.addChild(s);
			group.addChild(tg);
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
			group.addChild(tg);
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
			group.addChild(tg);
		}

		return group;
	}

	private Transform3D lookTowardsOriginFrom(Point3d point) {
		Transform3D move = new Transform3D();
		Vector3d up = new Vector3d(point.x, point.y + 1, point.z);
		move.lookAt(point, new Point3d(0.0d, 0.0d, 0.0d), up);

		return move;
	}
}
