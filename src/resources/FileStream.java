package resources;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.lwjgl.Sys;

public class FileStream {
	public static void saveConfig(String resolution, String aspect, int filter){
		try {
 
			File file = new File("res/config/application.cfg");
			file.createNewFile();
 
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("# Window size");
			bw.newLine();
			bw.write(resolution);
			bw.newLine();
			bw.write("# Aspect");
			bw.newLine();
			bw.write(aspect);
			bw.newLine();
			bw.write("# Filtering");
			bw.newLine();
			bw.write(filter+"");
			bw.close();
 
			System.out.println("Done");
 
		} catch (IOException e) {
			Sys.alert("Error", "Could not save the file. Check your available space, or the file name.\nPerhaps you don't have permission to save there too.");
		}
	}
	
	public static AppConfiguration loadConfig(){
		BufferedReader br = null;
		try {
			AppConfiguration ac = new AppConfiguration();
			String sCurrentLine;
			br = new BufferedReader(new FileReader("res/config/application.cfg"));
			while ((sCurrentLine = br.readLine()) != null) {
				if(sCurrentLine.startsWith("#")){
					if(sCurrentLine.split("#")[1].contains("Window size")){
						sCurrentLine = br.readLine();
						ac.resolution = sCurrentLine;
					} else if(sCurrentLine.split("#")[1].contains("Aspect")){
						sCurrentLine = br.readLine();
						ac.aspect = sCurrentLine;
					} else if(sCurrentLine.split("#")[1].contains("Filtering")){
						sCurrentLine = br.readLine();
						ac.filtering = Integer.parseInt(sCurrentLine);
					}
				}
			}
			return ac;
 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}
}
