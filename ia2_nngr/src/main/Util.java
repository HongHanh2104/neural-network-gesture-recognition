package main;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import neural_network.NeuralNetwork;
import train.ImageTrainingInstance;
import train.TrainingParameters;
import training.TrainingSet;

public class Util
{
	private static final String trainedNetworks = "trained_networks/";
	
	public static TrainingSet generateTrainingSet(List<String> dirs)
	{
		TrainingSet ts = null;
		
		int size = dirs.size();
		double outputs[][] = new double[size][size];
		
		for(int j = 0 ; j < size ; j++)
		for(int k = 0 ; k < size ; k++)
			outputs[j][k] = (k == j) ? 1.0 : 0.0;
		
		int i = 0;
		for(String dir : dirs)
		{
			File actual = new File(dir);
			
	        for(File f : actual.listFiles())
	        {
	        	String filename = f.getAbsolutePath();
	        	if(filename.endsWith(".png"))
	        	{
	        		ImageTrainingInstance iti = new ImageTrainingInstance(filename, outputs[i]);
	        		if(ts == null)
	        		{
	        			ts = new TrainingSet(iti.getInputs().length, iti.getOutputs().length);
	        		}
	        		ts.addInstance(iti);
	        	}
	        }
	        i++;
		}
		
		return ts;
	}
	
	public static void createAndTrainNetwork(String name, TrainingParameters tp, List<String> dirs, int[] hiddenStructure)
	{		
		TrainingSet ts = generateTrainingSet(dirs);
		
		int[] structure = new int[hiddenStructure.length + 2];
		
		structure[0] = ts.getNumberOfInputs();
		System.arraycopy(hiddenStructure, 0, structure, 1, hiddenStructure.length);
		structure[structure.length - 1] = ts.getNumberOfOutputs();
		
		NeuralNetwork nn = new NeuralNetwork(structure);
		
		System.out.println("Training neural network \"" + name + "\"");
		trainNeuralNetwork(nn, ts, tp, true);
		
		try
		{
			File file = new File(trainedNetworks + name + ".nn");
			nn.serialize(file);
		}
		catch(Exception e){ e.printStackTrace(); }
	}
	
	public static void testInstances(String name, List<String> dirs)
	{
		NeuralNetwork nn = null;

		try
		{
			File file = new File(trainedNetworks + name + ".nn");
			nn = NeuralNetwork.unserialize(file);
		}
		catch(Exception e){ e.printStackTrace(); }
		
		System.out.println("Testing neural network \"" + name + "\"");
		for(String dir : dirs)
		{
			System.out.println(dir + " :");;
			showOutput(nn, dir);
		}
		
		System.out.println();
	}
	
	
	private static void trainNeuralNetwork(NeuralNetwork nn, TrainingSet ts, TrainingParameters tp, boolean verbose)
	{
		int iterations;
		double error = 0;
		
		int iter = tp.numIterations / tp.errorChecks;
		for(iterations = 0 ; iterations < tp.errorChecks; iterations++)
		{
			nn.train(tp.learningMethod, ts, iter, tp.learningRate, tp.momentum);
			
			error = nn.rootMeanSquaredError(ts);
			
			if(verbose) System.out.println(error);
			
			if(Math.abs(error) < tp.maxError) break;
		}
		
		if(verbose) System.out.println(iterations * iter + " " + error);
	}
	

	private static void showOutput(NeuralNetwork nn, String dir)
	{
		File f = new File(dir);
		
        for(File ff : f.listFiles())
        {
        	String filename = ff.getAbsolutePath();
        	if(filename.endsWith(".png"))
        	{
	        	ImageTrainingInstance iti = new ImageTrainingInstance(filename, null);
	        	nn.setInputs(iti.getInputs());
	        	System.out.println(Arrays.toString(nn.getOutput()));
        	}
        }
	}
}
