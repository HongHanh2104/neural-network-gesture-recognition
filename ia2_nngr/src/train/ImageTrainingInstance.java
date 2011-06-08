package train;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;

import javax.imageio.ImageIO;

import training.ITrainingInstance;

import neural_network.SimpleValue;


public class ImageTrainingInstance implements ITrainingInstance
{
	public BufferedImage image;
	public int width;
	public int height;
	
	private SimpleValue[] inputs;
	private SimpleValue[] outputs;
	
		
	public ImageTrainingInstance(String filename, SimpleValue[] outputs)
	{
		setImage(filename);
		processInputs();
		this.outputs = outputs;
	}
	
	@Override
	public SimpleValue[] getInputs()
	{
		return inputs;
	}

	@Override
	public SimpleValue[] getOutputs()
	{
		return outputs;
	}
	
	public String toString()
	{
		return Arrays.toString(inputs) + " -> " + Arrays.toString(outputs);
	}
	
	private void setImage(String filename)
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
	
	private void processInputs()
	{
		this.inputs = new SimpleValue[width*height];
		byte[] pixels = (byte[]) image.getData().getDataElements(0, 0, width, height, null);
		
		for(int i = 0; i < this.inputs.length; i++)
		{
			int grayValue = pixels[i] & 0xFF;
            double val = ((double)grayValue) / 255.0;
            
			this.inputs[i] = new SimpleValue(val);
		}
	}
	
}
