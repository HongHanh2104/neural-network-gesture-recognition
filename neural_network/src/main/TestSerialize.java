package main;

import training.ITrainingInstance;
import training.TrainingSet;
import neural_network.NeuralNetwork;
import neural_network.Neuron;

public class TestSerialize {

	public static void main(String args[])
	{
		NeuralNetwork nn = new NeuralNetwork(new int[]{2, 4, 1});
		
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
		
		double learningRate = 0.1;
		double momentum = 0.9;
		
		double error = 0;
		int iterations = 0;
		for(iterations = 0; iterations < 1000; iterations++)
		{
			nn.train(NeuralNetwork.LearningMethod.INCREMENTAL, ts, 1000, learningRate, momentum);
			
			error = nn.rootMeanSquaredError(ts);
			
			if(Math.abs(error) < 0.001) break;
		}
		

		Neuron n = new Neuron(5);
		Serializer.Serialize("hola.xml", nn);
		
		System.out.println("END");
	}
}
