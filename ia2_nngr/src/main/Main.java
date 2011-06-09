package main;

import java.io.File;
import java.util.Arrays;


import train.ImageTrainingInstance;
import training.TrainingSet;
import neural_network.NeuralNetwork;
import neural_network.NeuralNetwork.LearningMethod;
import neural_network.SimpleValue;

public class Main {

	static String workspace = "/home/jacano/dev/workspace/";
	
	public static void main(String[] args) 
	{
		int numInputs = 30*30;
		int numOutputs = 2;
		int numHiddenNeurons = 100;
		
		NeuralNetwork nn = new NeuralNetwork(new int[]{numInputs, numHiddenNeurons, numOutputs});
		
		TrainingSet ts = new TrainingSet(numInputs, numOutputs);

		String imageOutputPath = workspace + "images/output/";
		
		String dir1 = imageOutputPath + "train/hand/right/fingers/0/";
		
		SimpleValue outputs1[] = new SimpleValue[numOutputs];
		outputs1[0] = new SimpleValue(1.0);
		outputs1[1] = new SimpleValue(0.0);
		trainImageDir(outputs1, ts, dir1);
		
		String dir2 = imageOutputPath + "train/hand/right/fingers/5/";
		
		SimpleValue outputs2[] = new SimpleValue[numOutputs];
		outputs2[0] = new SimpleValue(0.0);
		outputs2[1] = new SimpleValue(1.0);
		trainImageDir(outputs2, ts, dir2);
		
		
		double learningRate = 0.4;
		double momentum = 0.9;
		
		int numIterations = 1000;
		int errorChecks = 50;
		LearningMethod lm = NeuralNetwork.LearningMethod.BATCH;
		
		System.out.println("Training network...");
		trainNeuralNetwork(nn, ts, learningRate, momentum, numIterations, errorChecks, lm, true);
		
		/*for(ITrainingInstance instance : ts)
		{
			nn.setInputs(instance.getInputs());
			System.out.println(instance + " - " + Arrays.toString(nn.getOutput()));
		}*/
		
		System.out.println("Testing new instances...");
		
		String dir3 = imageOutputPath + "test/hand/right/fingers/0/";
		ShowOutput(nn, dir3);
        
		String dir4 = imageOutputPath + "test/hand/right/fingers/5/";
		ShowOutput(nn, dir4);
		
		String TrainedNNPath = workspace + "trained_networks/";
		String dirnn = TrainedNNPath + "nn.xml";
		
        System.out.println("Serializing neural network...");
        Serializer.Serialize(dirnn, nn);
        
        
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
	
	private static void ShowOutput(NeuralNetwork nn, String dir)
	{
		File f = new File(dir);
		
        for(File ff : f.listFiles())
        {
        	String path = ff.getPath();
        	if(path.endsWith(".png"))
        	{
	        	ImageTrainingInstance iti = new ImageTrainingInstance(path, null);
	        	nn.setInputs(iti.getInputs());
	        	System.out.println(Arrays.toString(nn.getOutput()));
        	}
        }
	}

}
