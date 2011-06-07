package main;

import java.io.File;
import java.util.Arrays;


import train.TrainingImage;
import training.ITrainingInstance;
import training.TrainingSet;
import neural_network.NeuralNetwork;

public class Main {

	public static void main(String[] args) 
	{
		int numInputs = 30*30;
		int numOutputs = 1;
		int numHiddenNeurons = 10;
		
		NeuralNetwork nn = new NeuralNetwork(new int[]{numInputs, numHiddenNeurons, numOutputs});
		
		TrainingSet ts = new TrainingSet(numInputs, numOutputs);

		String dir = "./train/hand/right/fingers/0/";
		
		File actual = new File(dir);
        for(File f : actual.listFiles())
        {
        	String filename = dir + f.getName();
        	if(filename.endsWith(".png"))
        	{
        		TrainingImage ti = new TrainingImage(filename);
        		
        		double[] inputs = ti.getTrainingInputs();
        		double[] outputs = new double[numOutputs];
        		outputs[0] = 1;
        		
        		ITrainingInstance newInstance = new SimpleTrainingInstance(inputs, outputs);
        		ts.addInstance(newInstance);
        	}
        }
		

		// Train		
		double learningRate = 0.1;
		double momentum = 0.9;
		
		
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
		
		// Test
		for(ITrainingInstance instance : ts)
		{
			nn.setInputs(instance.getInputs());
			System.out.println(instance + " - " + Arrays.toString(nn.getOutput()));
		}
		
		
		System.out.println("END");
	}

}
