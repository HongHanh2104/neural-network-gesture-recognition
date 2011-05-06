package graphictest;

import neural_network.NeuralNetwork;
import processing.core.PApplet;
import training.TrainingSet;

public class GNNTester
{
	private GNNTesterApplet applet;
	
	public GNNTester(NeuralNetwork nn, TrainingSet ts)
	{
		if(nn.getNumberOfInputs() != 2)
			throw new IllegalArgumentException("The network to test must have 2 inputs!");
		if(nn.getNumberOfOutputs() != 1)
			throw new IllegalArgumentException("The network to test must have 1 output!");
		
		if(ts.getNumberOfInputs() != 2)
			throw new IllegalArgumentException("The training instances must have 2 inputs!");
		if(ts.getNumberOfOutputs() != 1)
			throw new IllegalArgumentException("The training instances must have 1 output!");
		
		applet = new GNNTesterApplet(nn, ts, 25, 20);
		PApplet.runSketch(new String[]{"Graphic Neural Network Test"}, applet);
	}
	
	public void updateInfo()
	{
		applet.updateInfo();
	}
}
