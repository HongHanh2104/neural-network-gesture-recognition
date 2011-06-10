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
	private SimpleValue[] inputs;
	private SimpleValue[] outputs;
	
	public ImageTrainingInstance(String filename, double[] outputs)
	{
		setInputs(filename);
		setOutputs(outputs);
	}
	
	private void setInputs(String filename)
	{
		BufferedImage image = null;
		int width = 0;
		int height = 0;
		
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
		
		this.inputs = new SimpleValue[width*height];
		byte[] pixels = (byte[]) image.getData().getDataElements(0, 0, width, height, null);
		
		for(int i = 0; i < this.inputs.length; i++)
		{
			int grayValue = pixels[i] & 0xFF;
            double val = ((double)grayValue) / 255.0;
            
			this.inputs[i] = new SimpleValue(val);
		}
	}
	
	private void setOutputs(double[] outputs) 
	{
		if(outputs != null)
		{
			this.outputs = new SimpleValue[outputs.length];
			for(int i = 0 ; i < outputs.length; i++)
				this.outputs[i] = new SimpleValue(outputs[i]);
		}
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
}
