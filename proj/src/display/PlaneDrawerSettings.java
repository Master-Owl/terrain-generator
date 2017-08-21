package display;

import perlinNoise.Settings;

public class PlaneDrawerSettings extends Settings {

	private float 	scaleSize;
	private float 	heightAmplify;
	private boolean wireframe;
	private boolean autoRotate;
	
	private void setDefaults(){
		scaleSize = 3.0f;
		heightAmplify = 10.0f;
		wireframe = true;
		autoRotate = true;
	}
	
	public PlaneDrawerSettings() {
		super();
		setDefaults();
	}

	public PlaneDrawerSettings(Settings copy) {
		super(copy);
		if (copy instanceof PlaneDrawerSettings){
			PlaneDrawerSettings pds = (PlaneDrawerSettings)copy;
			this.scaleSize = pds.scaleSize;
			this.heightAmplify = pds.heightAmplify;
			this.wireframe = pds.wireframe;
			this.autoRotate = pds.autoRotate;
		}
		else
			setDefaults();
	}
	
	public void setDetailLevel(int detail){
		setArrWidth(detail);
		setArrHeight(detail);
	}
	
	public int getDetailLevel(){
		int width = getArrWidth();
		int height = getArrHeight();
		return width >= height ? width : height;
	}

	public float getScaleSize() {
		return scaleSize;
	}

	public float getHeightAmplify() {
		return heightAmplify;
	}

	public boolean useWireframe() {
		return wireframe;
	}

	public void setScaleSize(float scaleSize) {
		this.scaleSize = scaleSize;
	}

	public void setHeightAmplify(float heightAmplify) {
		this.heightAmplify = heightAmplify;
	}

	public void useWireframe(boolean wireframe) {
		this.wireframe = wireframe;
	}

	public boolean getAutoRotate() {
		return autoRotate;
	}

	public void setAutoRotate(boolean autoRotate) {
		this.autoRotate = autoRotate;
	}
}
