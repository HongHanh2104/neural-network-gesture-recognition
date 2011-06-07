package main;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

import resiz0r.Resiz0r;
import training.ITrainingInstance;
import training.TrainingSet;
import neural_network.NeuralNetwork;

public class Main {

	public static void main(String[] args) 
	{
		int numInputs = 2;
		int numOutputs = 1;
		int numHiddenNeurons = 4;
		
		NeuralNetwork nn = new NeuralNetwork(new int[]{numInputs, numHiddenNeurons, numOutputs});
		
		TrainingSet ts = new TrainingSet(numInputs, numOutputs);

		String dir = "./train/hand/right/fingers/0/";
		
		File actual = new File(dir);
        for(File f : actual.listFiles())
        {
        	String filename = dir + f.getName();
        	if(filename.endsWith(".png"))
        	{

        	}
        }
		
		
		double[] inputs = new double[numInputs];
		double[] outputs = new double[numOutputs];
		ITrainingInstance newInstance = new SimpleTrainingInstance(inputs, outputs);
		
		ts.addInstance(newInstance);
		
		
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
