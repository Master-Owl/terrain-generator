package display;

import javax.vecmath.Point3f;

import terrain.MapInterpreter;
import terrain.enums.Biome;

public class TerrainMap {
	private TerrainPoint[] terrainMap;
	private int height;
	private int width;
	
	public TerrainMap(){
		terrainMap = null;
		height = 0;
		width = 0;
	}
	
	public TerrainMap(float[][] elevationMap){
		height = elevationMap.length;
		width = elevationMap[0].length;
		terrainMap = new TerrainPoint[height * width];
		
		for (int row = 0; row < height; ++row){
			for (int col = 0; col < width; ++col){
				Point3f point = new Point3f();
				
				point.x = col;
				point.y = elevationMap[row][col];
				point.z = row;
				
				terrainMap[(row * width) + col] = new TerrainPoint(point);
			}
		}
	}
	
	public TerrainMap(float[][] elevationMap, float[][] temperatureMap, float[][] moistureMap) throws Exception{
		height = elevationMap.length;
		width = elevationMap[0].length;
		Exception e = new Exception("The given arrays must be the same size.");
		
		if (height != temperatureMap.length
			|| height != moistureMap.length
			|| width  != temperatureMap[0].length
			|| width  != moistureMap[0].length)
			throw e;		
		
		terrainMap = new TerrainPoint[height * width];
		
		for (int row = 0; row < height; ++row){
			for (int col = 0; col < width; ++col){
				Point3f point = new Point3f();
				
				point.x = col;
				point.y = elevationMap[row][col];
				point.z = row;
				
				Biome b = MapInterpreter.GetBiome(
							elevationMap[row][col],
							temperatureMap[row][col],
							moistureMap[row][col]);
				
				terrainMap[(row * width) + col] = new TerrainPoint(point, b);
			}
		}
	}
	
	public TerrainPoint[] getMap() { return terrainMap; }
	
	public TerrainPoint getTerrainPoint(int x, int y){
		return terrainMap[(x * width) + y];
	}

	
	public void setTerrainMap(TerrainPoint[] terrainMap) {
		this.terrainMap = terrainMap;
	}

}
