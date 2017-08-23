package terrain;

import javax.vecmath.Point3f;

import terrain.enums.Biome;

public class TerrainPoint {
	private Point3f point;
	private Biome biome;
	
	public TerrainPoint(){
		point = new Point3f();
		biome = null;
	}
	
	public TerrainPoint(Point3f point){
		this.point = point;
		biome = null;
	}
	
	public TerrainPoint(Point3f point, Biome biome){
		this.point = point;
		this.biome = biome;
	}

	public Point3f getPoint() {
		return point;
	}

	public Biome getBiome() {
		return biome;
	}

	public void setPoint(Point3f point) {
		this.point = point;
	}

	public void setBiome(Biome biome) {
		this.biome = biome;
	}	
}
