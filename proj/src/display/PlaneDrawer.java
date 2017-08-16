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
import javax.media.j3d.Group;
import javax.media.j3d.LineStripArray;
import javax.media.j3d.Material;
import javax.media.j3d.PolygonAttributes;
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

	private Point3d centerPoint;
	private OrbitBehavior behavior;
	private SimpleUniverse universe;
	private Canvas3D canvas;
	private BranchGroup group;

	public PlaneDrawer(float[][] noiseMap) {
		this.noiseMap = noiseMap;
		height = noiseMap.length;
		width = noiseMap[0].length;
		centerPoint = new Point3d();

		behavior = null;
		universe = null;
		canvas = null;
		group = null;
	}

	public void setNoiseMap(float[][] noiseMap) {
		this.noiseMap = noiseMap;
		height = noiseMap.length;
		width = noiseMap[0].length;
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

		// Row lines
		for (int row = 0; row < height; ++row) {
			Point3f[] rowVertices = new Point3f[width];

			for (int col = 0; col < width; ++col) {
				Point3f vert = new Point3f();

				vert.x = col * scaleLengthWidth;
				vert.y = noiseMap[row][col] * scaleHeight;
				vert.z = row * scaleLengthWidth;

				rowVertices[col] = vert;

				if (row == height / 2 && col == width / 2) {
					centerPoint.x = vert.x;
					centerPoint.y = vert.y;
					centerPoint.z = vert.z;
				}
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

			}

			mesh[height + col] = new LineStripArray(height, GeometryArray.COORDINATES, new int[] { height });
			mesh[height + col].setCoordinates(0, colVertices);
		}

		// System.out.println("Center: " + centerPoint);

		return mesh;
	}

	public void init(boolean useWireframe, float amplify, float size) {
		removeAll();
		setLayout(new BorderLayout());

		GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
		canvas = new Canvas3D(config);
		universe = new SimpleUniverse(canvas);
		behavior = new OrbitBehavior(canvas, OrbitBehavior.REVERSE_ROTATE);
		group = new BranchGroup();
		BranchGroup group2 = new BranchGroup();

		group.setCapability(Group.ALLOW_CHILDREN_READ);
		group.setCapability(Group.ALLOW_CHILDREN_WRITE);
		group.setCapability(Group.ALLOW_CHILDREN_EXTEND);
		group2.setCapability(BranchGroup.ALLOW_DETACH);

		float scale = getScale(size); // The width and length

		add("Center", canvas);

		if (useWireframe)
			group2 = getAxisRef();
		initPlane(group2, amplify, scale);

		DirectionalLight dl = getLight(Color.cyan);
		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
		TransformGroup objTrans = new TransformGroup();
		Transform3D initialView = lookTowardsOriginFrom(new Point3d(-1.0, 0.75, -12.0));

		dl.setInfluencingBounds(bounds);
		objTrans.addChild(group2);

		behavior.setSchedulingBounds(bounds);
		behavior.setRotXFactor(1);
		behavior.setRotYFactor(1);

		BranchGroup children = new BranchGroup();
		children.setCapability(BranchGroup.ALLOW_DETACH);
		children.addChild(objTrans);
		children.addChild(dl);

		group.addChild(children);

		universe.getViewingPlatform().setViewPlatformBehavior(behavior);
		universe.getViewingPlatform().getViewPlatformTransform().setTransform(initialView);
		universe.addBranchGraph(group);
	}

	public void initPlane(BranchGroup group, float amplify, float scale) {
		LineStripArray[] mesh = getMesh(amplify, scale);
		float RATIO = -(float) centerPoint.y;

		for (int i = 0; i < mesh.length; ++i) {
			Shape3D meshLine = new Shape3D(mesh[i], createAppearance(true));

			TransformGroup centerPlane = new TransformGroup();
			Transform3D centerTrans = new Transform3D();
			Vector3f centerVect = new Vector3f(-(width / height) * (width / 8.0f), RATIO,
					-(height / width) * (height / 8.0f));

			centerTrans.setTranslation(centerVect);
			centerPlane.setTransform(centerTrans);
			centerPlane.addChild(meshLine);

			group.addChild(centerPlane);
		}
	}

	public void redraw(float amplify, float size) {
		group.removeChild(group.getChild(0));

		BranchGroup group2 = new BranchGroup();
		initPlane(group2, amplify, getScale(size));

		TransformGroup objTrans = new TransformGroup();
		objTrans.addChild(group2);

		BranchGroup children = new BranchGroup();
		children.setCapability(BranchGroup.ALLOW_DETACH);
		children.addChild(objTrans);
		children.addChild(getLight(Color.cyan));

		group.addChild(children);
	}

	private float getScale(float size) {
		return size / ((width + height) / 2.0f);
	}

	private DirectionalLight getLight(Color c) {
		return new DirectionalLight(new Color3f(c), new Vector3f(4.0f, -7.0f, 12.0f));
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
