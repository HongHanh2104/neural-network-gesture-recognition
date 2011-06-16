package main;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import resiz0r.Resiz0r;

public class Resize
{
	public static void main(String[] args)
	{
		String inputDir = "input/";
		String outputPath = "output/";
		
		cloneAndResizeR(outputPath, inputDir);
        
        System.out.println("End!");
	}
	
	private static void cloneAndResizeR(String output, String dir) 
	{
		File f = new File(dir);
		
        for(File ff : f.listFiles())
        {
        	if(!ff.isHidden())
        	{
	        	if(ff.isDirectory())
	        	{
	        		cloneAndResizeR(output + "/" + ff.getName(), ff.getAbsolutePath());
	        	}
	        	else if(ff.isFile())
	        	{
	        		String input = ff.getAbsolutePath();
	        		String out = output + "/" + ff.getName();
	        		try 
		    		{
	        			File outputDir = new File(output);
	        			if(!outputDir.exists()) outputDir.mkdirs();

        				BufferedImage img = Resiz0r.open(input, 30, 30);
        				ImageIO.write(img, "png", new File(out));
        				System.out.println("Resized image " + ff.getAbsolutePath());
		    		}
		    		catch (Exception e) { }
	        	}
        	}
        }
	}

}
