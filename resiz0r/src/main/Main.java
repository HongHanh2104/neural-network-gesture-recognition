package main;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import resiz0r.Resiz0r;


public class Main {

	static String workspace = "/home/jacano/dev/workspace/";
	
	public static void main(String[] args)
	{
		String imagePath = workspace + "images/";
		
		String outputFolder = "output/";
		String output = imagePath + outputFolder;
		
		String folder1 = "test";
		String dir1 = imagePath + folder1;
        cloneAndResize(output + folder1, dir1);
        
        String folder2 = "train";
		String dir2 = imagePath + folder2;
        cloneAndResize(output + folder2, dir2);
        
        System.out.println("End!");
	}

	private static void cloneAndResize(String output, String dir) 
	{
		File f = new File(dir);
		
        for(File ff : f.listFiles())
        {
        	if(!ff.isHidden())
        	{
	        	if(ff.isDirectory())
	        	{
	        		cloneAndResize(output + "/" + ff.getName(), ff.getPath());
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
