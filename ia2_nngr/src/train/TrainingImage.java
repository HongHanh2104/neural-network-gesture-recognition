package train;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;


public class TrainingImage 
{
	public BufferedImage image;
	public int width;
	public int height;
	
	public TrainingImage(String filename)
	{
		try 
		{
			Image originalImage = ImageIO.read(new File(filename));
			width = originalImage.getWidth(null);
			height = originalImage.getHeight(null);
			
			image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
			Graphics2D graphics = image.createGraphics();
			graphics.drawImage(originalImage, 0, 0, width, height, null);
			graphics.dispose();
		} 
		catch (Exception e) { System.out.println("Image error!"); }
	}
	
	public double[] getTrainingInputs()
	{
		double res[] = new double[width*height];

        byte[] pixels = (byte[]) image.getData().getDataElements(0, 0, width, height, null);
        for (int i = 0; i < res.length; i++) 
        {
            res[i] = (double) (pixels[i] & 0xFF);
        }
		
		return res;
	}
	
}
