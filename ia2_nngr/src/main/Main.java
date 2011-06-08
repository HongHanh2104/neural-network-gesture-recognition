package main;

import java.io.File;
import java.util.Arrays;


import train.ImageTrainingInstance;
import training.ITrainingInstance;
import training.TrainingSet;
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

		String dir1 = "./train/hand/right/fingers/0/";
		
		SimpleValue outputs1[] = new SimpleValue[numOutputs];
		outputs1[0] = new SimpleValue(1.0);
		outputs1[1] = new SimpleValue(0.0);
		trainImageDir(outputs1, ts, dir1);
		
		String dir2 = "./train/hand/right/fingers/5/";
		
		SimpleValue outputs2[] = new SimpleValue[numOutputs];
		outputs2[0] = new SimpleValue(0.0);
		outputs2[1] = new SimpleValue(1.0);
		trainImageDir(outputs2, ts, dir2);
		

		// Train		
		double learningRate = 0.1;
		double momentum = 0.98;
		
		int numIterations = 100;
		int errorChecks = 50;
		LearningMethod lm = NeuralNetwork.LearningMethod.BATCH;
		
		
		trainNeuralNetwork(nn, ts, learningRate, momentum, numIterations, errorChecks, lm, true);
		
		
		// Test
		for(ITrainingInstance instance : ts)
		{
			nn.setInputs(instance.getInputs());
			System.out.println(instance + " - " + Arrays.toString(nn.getOutput()));
		}
		
		
		System.out.println("END");
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
	
	private static void trainImageDir(SimpleValue[] outputs, TrainingSet ts, String dir) 
	{
		File actual = new File(dir);
        for(File f : actual.listFiles())
        {
        	String filename = dir + f.getName();
        	if(filename.endsWith(".png"))
        	{
        		ImageTrainingInstance iti = new ImageTrainingInstance(filename, outputs);
        		ts.addInstance(iti);
        	}
        }
	}

}
