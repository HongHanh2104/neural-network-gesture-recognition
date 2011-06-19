package main;

import java.util.ArrayList;
import java.util.List;

import neural_network.NeuralNetwork;
import train.TrainingParameters;

public class TrainNeuralNetworks
{

	public static void main(String[] args)
	{
//		trainOpenCloseRightHand();
//		trainRightHandLeftHand();
//		trainNumFingers();
//		trainVowels();
	}
	
	public static void trainOpenCloseRightHand()
	{		
		TrainingParameters tp = new TrainingParameters();
		tp.learningRate = 0.005;
		tp.momentum = 0.2;
		tp.numIterations = 50000;
		tp.errorChecks = 500;
		tp.maxError = 0.001;
		tp.learningMethod = NeuralNetwork.LearningMethod.BATCH;
		
		List<String> dirs = new ArrayList<String>();
		dirs.add("train/right/fingers/0/");
		dirs.add("train/right/fingers/5/");
		
		int[] hiddenLayers = new int[]{ 4 };
		
		Util.createAndTrainNetwork("OpenCloseRightHand", tp, dirs, hiddenLayers);
	}
	
	public static void trainRightHandLeftHand()
	{		
		TrainingParameters tp = new TrainingParameters();
		tp.learningRate = 0.01;
		tp.momentum = 0.2;
		tp.numIterations = 50000;
		tp.errorChecks = 500;
		tp.maxError = 0.001;
		tp.learningMethod = NeuralNetwork.LearningMethod.BATCH;
		
		List<String> dirs = new ArrayList<String>();
		dirs.add("train/right/fingers/5/");
		dirs.add("train/left/fingers/5/");
		
		int[] hiddenLayers = new int[]{ 4 };
		
		Util.createAndTrainNetwork("RightHandLeftHand", tp, dirs, hiddenLayers);
	}
	
	public static void trainNumFingers()
	{		
		TrainingParameters tp = new TrainingParameters();
		tp.learningRate = 0.002;
		tp.momentum = 0.2;
		tp.numIterations = 100000;
		tp.errorChecks = 500;
		tp.maxError = 0.001;
		tp.learningMethod = NeuralNetwork.LearningMethod.BATCH;
		
		List<String> dirs = new ArrayList<String>();
		dirs.add("train/right/fingers/0/");
		dirs.add("train/right/fingers/1/");
		dirs.add("train/right/fingers/2/");
		dirs.add("train/right/fingers/3/");
		dirs.add("train/right/fingers/4/");
		dirs.add("train/right/fingers/5/");
		
		int[] hiddenLayers = new int[]{ 4 };
		
		Util.createAndTrainNetwork("NumFingers", tp, dirs, hiddenLayers);
	}
	
	public static void trainVowels()
	{		
		TrainingParameters tp = new TrainingParameters();
		tp.learningRate = 0.005;
		tp.momentum = 0.3;
		tp.numIterations = 150000;
		tp.errorChecks = 500;
		tp.maxError = 0.001;
		tp.learningMethod = NeuralNetwork.LearningMethod.BATCH;
		
		List<String> dirs = new ArrayList<String>();
		dirs.add("train/right/vowels/a/");
		dirs.add("train/right/vowels/e/");
		dirs.add("train/right/vowels/i/");
		dirs.add("train/right/vowels/o/");
		dirs.add("train/right/vowels/u/");
		
		int[] hiddenLayers = new int[]{ 4 };
		
		Util.createAndTrainNetwork("Vowels", tp, dirs, hiddenLayers);
	}

}
