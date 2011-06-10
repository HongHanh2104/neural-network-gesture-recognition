package main;

import java.io.File;
import java.util.Arrays;


import train.ImageTrainingInstance;
import training.TrainingSet;
import util.Util;
import neural_network.NeuralNetwork;
import neural_network.NeuralNetwork.LearningMethod;
import neural_network.SimpleValue;

public class Main {

	public static void main(String[] args) 
	{		
		int numInputs = 30*30;
		int numOutputs = 2;
		int numHiddenNeurons = 100;
		
		NeuralNetwork nn = new NeuralNetwork(new int[]{numInputs, numHiddenNeurons, numOutputs});
		
		TrainingSet ts = new TrainingSet(numInputs, numOutputs);
		addTrainingInstancesFromDir(ts, "train/hand/right/fingers/0/", new double[]{1.0, 0.0});
		addTrainingInstancesFromDir(ts, "train/hand/right/fingers/5/", new double[]{0.0, 1.0});
		
		
		double learningRate = 0.01;
		double momentum = 0.2;
		int numIterations = 1000;
		int errorChecks = 50;
		LearningMethod lm = NeuralNetwork.LearningMethod.BATCH;
		
		System.out.println("Training network...");
		trainNeuralNetwork(nn, ts, learningRate, momentum, numIterations, errorChecks, lm, true);
		
		System.out.println("Testing new instances...");
		ShowOutput(nn, "test/hand/right/fingers/0/");
		ShowOutput(nn, "test/hand/right/fingers/5/");
		
		
        System.out.println("Serializing neural network...");
        Serializer.Serialize("trained_networks/nn.xml", nn);
        
		System.out.println("END");
	}

	public static void addTrainingInstancesFromDir(TrainingSet ts, String dir, double[] doubleOutput)
	{
		SimpleValue outputs[] = new SimpleValue[doubleOutput.length];
		for(int i = 0 ; i < doubleOutput.length ; i++) outputs[i] = new SimpleValue(doubleOutput[i]);
		
		String workspace = Util.getEclipseWorkspace();
		String path = workspace + "images/output/" + dir;
		File actual = new File(path);
		
        for(File f : actual.listFiles())
        {
        	String filename = f.getAbsolutePath();
        	if(filename.endsWith(".png"))
        	{
        		ImageTrainingInstance iti = new ImageTrainingInstance(filename, outputs);
        		ts.addInstance(iti);
        	}
        }
	}
	
	private static void trainNeuralNetwork(NeuralNetwork nn, TrainingSet ts, double learningRate, double momentum, int numIterations, int errorChecks, LearningMethod lm, boolean verbose)
	{
		int iterations;
		double error = 0;
		
		int iter = numIterations/errorChecks;
		for(iterations = 0 ; iterations < errorChecks; iterations++)
		{
			nn.train(lm, ts, iter, learningRate, momentum);
			
			error = nn.rootMeanSquaredError(ts);
			
			if(verbose) System.out.println(error);
			
			if(Math.abs(error) < 0.001) break;
		}
		
		if(verbose) System.out.println(iterations*iter + " " + error);
	}
	

	private static void ShowOutput(NeuralNetwork nn, String dir)
	{
		String workspace = Util.getEclipseWorkspace();
		String path = workspace + "images/output/" + dir;
		File f = new File(path);
		
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
