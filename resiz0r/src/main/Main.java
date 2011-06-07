package main;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import resiz0r.Resiz0r;

public class Main {

	public static void main(String[] args) throws IOException {
		
		String dir = "./images";
		String output = "./output";
		
		File f = new File(dir);

        cloneAndResize(output, f);
        
        System.out.println("End!");
	}

	private static void cloneAndResize(String output, File f) 
	{
        for(File ff : f.listFiles())
        {
        	if(!ff.isHidden())
        	{
	        	if(ff.isDirectory())
	        	{
	        		cloneAndResize(output + "/" + ff.getName(), ff);
	        	}
	        	else if(ff.isFile())
	        	{
	        		String input = ff.getPath();
	        		String out = output + "/" + ff.getName();
	        		try 
		    		{
	        			File outputDir = new File(output);
	        			if(!outputDir.exists()) outputDir.mkdirs();
		    			ImageIO.write(Resiz0r.open(input, 30, 30), "png", new File(out));
		    		}
		    		catch (Exception e) { }
	        	}
        	}
        }
	}

}
