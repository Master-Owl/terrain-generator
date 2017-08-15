package display;

import perlinNoise.Settings;

public class PlaneDrawerSettings extends Settings {

	private float 	scaleSize;
	private float 	heightAmplify;
	private boolean wireframe;
	
	private void setDefaults(){
		scaleSize = 3.0f;
		heightAmplify = 10.0f;
		wireframe = true;
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
		}
		else
			setDefaults();
	}

	public float getScaleSize() {
		return scaleSize;
	}

	public float getHeightAmplify() {
		return heightAmplify;
	}

	public boolean wireframe() {
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
	
	

}
