package main;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import resiz0r.Resiz0r;


public class Main {

	public static void main(String[] args)
	{
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

        				BufferedImage img = Resiz0r.open(input, 30, 30);
        				ImageIO.write(img,"png", new File(out));
		    		}
		    		catch (Exception e) { }
	        	}
        	}
        }
	}

}
