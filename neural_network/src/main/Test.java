package main;

import graphictest.GNNTester;

import java.io.File;
import java.util.Arrays;

import neural_network.NeuralNetwork;
import training.ITrainingInstance;
import training.TrainingSet;

public class Test
{
	private static enum TestType {CONSOLE, GRAPHIC};
	private static final TestType type = TestType.CONSOLE;
	
	public static void main(String[] args) throws InterruptedException
	{	
		// Training set for XOR function
		TrainingSet ts = new TrainingSet(2, 1);
		for(int i = 0; i < 4; i++)
		{
			int input1 = (i >> 0) & 1;
			int input2 = (i >> 1) & 1;
			int output = (input1 ^ input2) & 1;
			
			double[] inputs = new double[]{input1, input2};
			double[] outputs = new double[]{output};
			
			ITrainingInstance newInstance = new SimpleTrainingInstance(inputs, outputs);
			
			ts.addInstance(newInstance);
		}
		
		NeuralNetwork nn = new NeuralNetwork(new int[]{2, 4, 1});
		
		// Train		
		double learningRate = 0.1;
		double momentum = 0.9;
		
		switch(type)
		{
			case CONSOLE:
				double error = 0;
				int iterations = 0;
				for(iterations = 0; iterations < 1000; iterations++)
				{
					nn.train(NeuralNetwork.LearningMethod.INCREMENTAL, ts, 1000, learningRate, momentum);
					
					error = nn.rootMeanSquaredError(ts);
					
					System.out.println(error);
					
					if(Math.abs(error) < 0.001) break;
				}
				
				System.out.println(iterations + " " + error);
				
				// Test serialize
				System.out.println("Serializing...");
				try
				{
					File file = new File("nn.sav");
					nn.serialize(file);
				}
				catch(Exception e){ e.printStackTrace(); }
				
				// Test unserialize
				System.out.println("Unserializing...");
				try
				{
					File file = new File("nn.sav");
					nn = NeuralNetwork.unserialize(file);
				}
				catch(Exception e){ e.printStackTrace(); }
				
				// Test outputs
				for(ITrainingInstance instance : ts)
				{
					nn.setInputs(instance.getInputs());
					System.out.println(instance + " - " + Arrays.toString(nn.getOutput()));
				}
				
				break;
			case GRAPHIC:
				GNNTester tester = new GNNTester(nn, ts);
				
				for(int i = 0; i < 10000; i++)
				{
					nn.train(NeuralNetwork.LearningMethod.BATCH, ts, 1, learningRate, momentum);
					
					tester.updateInfo();
					
					Thread.sleep(50);
				}
				
				break;
		}
		
		System.out.println("Fin.");
	}
}
