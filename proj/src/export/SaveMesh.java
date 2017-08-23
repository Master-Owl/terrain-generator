package export;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import terrain.TerrainMap;
import terrain.TerrainPoint;

public class SaveMesh {
	public static String StringifyData(TerrainMap tm){
		TerrainPoint[] tpts = tm.getMap();
		StringBuilder builder = new StringBuilder();
		char space = ' ';
		char newln = '\n';
		for (TerrainPoint tp : tpts){
			builder.append(
					"v"	+ space +
					tp.getPoint().x + space + 
					tp.getPoint().y + space + 
					tp.getPoint().z + newln);
		}
		return builder.toString();
	}
	
	public static boolean WriteToFile(String path, String data){
		try {
			File f = new File(path);
			if (!f.exists()){
				f.getParentFile().mkdirs();
				f.createNewFile();
			}
			
			DataOutputStream os = new DataOutputStream(new FileOutputStream(f));
			
			os.writeChars(data);
			os.flush();
			os.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println(path);
		}
		return false;
	}
}
