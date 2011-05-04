package main;

import java.util.Arrays;

import training.ITrainingInstance;
import training.TrainingSet;

import neural_network.NeuralNetwork;

public class Test
{
	public static void main(String[] args)
	{		
		// Training set for XOR function
		TrainingSet trainingSet = new TrainingSet(2, 1);
		for(int i = 0; i < 4; i++)
		{
			int input1 = (i >> 0) & 1;
			int input2 = (i >> 1) & 1;
			int output = (input1 ^ input2) & 1;
			
			double[] inputs = new double[]{input1, input2};
			double[] outputs = new double[]{output};
			
			ITrainingInstance newInstance = new SimpleTrainingInstance(inputs, outputs);
			
			trainingSet.addInstance(newInstance);
		}
		
		NeuralNetwork nn = new NeuralNetwork(new int[]{2, 4, 1});
		
		// Train
		nn.setTrainingSet(trainingSet);
		
		double learningRate = 0.5;
		double momentum = 0.9;
		
		double error;
		
		int iterations = 0;
		do
		{
			error = nn.train(NeuralNetwork.LearningMethod.BATCH, 1000, learningRate, momentum);
			
			System.out.println(error);
			iterations++;
			if(iterations == 100) break;
		} while(Math.abs(error) > 0.0001);
		
		System.out.println(iterations + " " + error);
		
		// Test
		for(ITrainingInstance instance : trainingSet)
		{
			nn.setInputs(instance.getInputs());
			System.out.println(instance + " - " + Arrays.toString(nn.getOutput()));
		}
		
		System.out.println("Fin.");
	}
}
