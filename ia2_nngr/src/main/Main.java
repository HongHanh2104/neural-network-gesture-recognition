package main;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import neural_network.NeuralNetwork;
import train.ImageTrainingInstance;
import train.GroupTest;
import train.GroupTrain;
import train.TrainingParameters;
import training.TrainingSet;

public class Main {


	
	public static void main(String[] args) 
	{	
		//trainOpenCloseRightHand();
		testOpenCloseRightHand();
		
		//trainRightHandLeftHand();
		testRightHandLeftHand();
		
		//trainNumFingers();
		testNumFingers();
		
		//trainVowels();
		testVowels();
	}
	
	
	public static void trainVowels()
	{		
		TrainingParameters tp = new TrainingParameters();
		tp.learningRate = 0.001;
		tp.momentum = 0.3;
		tp.numIterations = 150000;
		tp.errorChecks = 50;
		tp.maxError = 0.001;
		tp.lm = NeuralNetwork.LearningMethod.BATCH;
		
		List<String> dirs = new ArrayList<String>();
		dirs.add("train/right/vowels/a/");
		dirs.add("train/right/vowels/e/");
		dirs.add("train/right/vowels/i/");
		dirs.add("train/right/vowels/o/");
		dirs.add("train/right/vowels/u/");
		
		GroupTrain pt = new GroupTrain();
		pt.networkName = "Vowels";
		pt.dirs = dirs;
		pt.tp = tp;
		
		int[] hiddenLayers = new int[]{ 4 };
		
		createAndTrainNetwork(pt, hiddenLayers);
	}
	
	
	public static void testVowels()
	{
		List<String> dirs = new ArrayList<String>();
		dirs.add("test/right/vowels/a/");
		dirs.add("test/right/vowels/e/");
		dirs.add("test/right/vowels/i/");
		dirs.add("test/right/vowels/o/");
		dirs.add("test/right/vowels/u/");
		
		GroupTest ptt = new GroupTest();
		ptt.networkName = "Vowels";
		ptt.dirs = dirs;
		
		testNewInstances(ptt);
	}
	
	
	public static void trainNumFingers()
	{		
		TrainingParameters tp = new TrainingParameters();
		tp.learningRate = 0.001;
		tp.momentum = 0.2;
		tp.numIterations = 100000;
		tp.errorChecks = 50;
		tp.maxError = 0.001;
		tp.lm = NeuralNetwork.LearningMethod.BATCH;
		
		List<String> dirs = new ArrayList<String>();
		dirs.add("train/right/fingers/0/");
		dirs.add("train/right/fingers/1/");
		dirs.add("train/right/fingers/2/");
		dirs.add("train/right/fingers/3/");
		dirs.add("train/right/fingers/4/");
		dirs.add("train/right/fingers/5/");
		
		GroupTrain pt = new GroupTrain();
		pt.networkName = "NumFingers";
		pt.dirs = dirs;
		pt.tp = tp;
		
		int[] hiddenLayers = new int[]{ 4 };
		
		createAndTrainNetwork(pt, hiddenLayers);
	}
	
	
	public static void testNumFingers()
	{
		List<String> dirs = new ArrayList<String>();
		dirs.add("test/right/fingers/0/");
		dirs.add("test/right/fingers/1/");
		dirs.add("test/right/fingers/2/");
		dirs.add("test/right/fingers/3/");
		dirs.add("test/right/fingers/4/");
		dirs.add("test/right/fingers/5/");
		
		GroupTest ptt = new GroupTest();
		ptt.networkName = "NumFingers";
		ptt.dirs = dirs;
		
		testNewInstances(ptt);
	}
	
	public static void trainRightHandLeftHand()
	{		
		TrainingParameters tp = new TrainingParameters();
		tp.learningRate = 0.01;
		tp.momentum = 0.2;
		tp.numIterations = 10000;
		tp.errorChecks = 50;
		tp.maxError = 0.001;
		tp.lm = NeuralNetwork.LearningMethod.BATCH;
		
		List<String> dirs = new ArrayList<String>();
		dirs.add("train/right/fingers/5/");
		dirs.add("train/left/fingers/5/");
		
		GroupTrain pt = new GroupTrain();
		pt.networkName = "RightHandLeftHand";
		pt.dirs = dirs;
		pt.tp = tp;
		
		int[] hiddenLayers = new int[]{ 4 };
		
		createAndTrainNetwork(pt, hiddenLayers);
	}
	
	
	public static void testRightHandLeftHand()
	{
		List<String> dirs = new ArrayList<String>();
		dirs.add("test/right/fingers/5/");
		dirs.add("test/left/fingers/5/");
		
		GroupTest ptt = new GroupTest();
		ptt.networkName = "RightHandLeftHand";
		ptt.dirs = dirs;
		
		testNewInstances(ptt);
	}

	
	public static void trainOpenCloseRightHand()
	{		
		TrainingParameters tp = new TrainingParameters();
		tp.learningRate = 0.01;
		tp.momentum = 0.2;
		tp.numIterations = 10000;
		tp.errorChecks = 50;
		tp.maxError = 0.001;
		tp.lm = NeuralNetwork.LearningMethod.BATCH;
		
		List<String> dirs = new ArrayList<String>();
		dirs.add("train/right/fingers/0/");
		dirs.add("train/right/fingers/5/");
		
		GroupTrain pt = new GroupTrain();
		pt.networkName = "OpenCloseRightHand";
		pt.dirs = dirs;
		pt.tp = tp;
		
		int[] hiddenLayers = new int[]{ 4 };
		
		createAndTrainNetwork(pt, hiddenLayers);
	}
	
	public static void testOpenCloseRightHand()
	{
		List<String> dirs = new ArrayList<String>();
		dirs.add("test/right/fingers/0/");
		dirs.add("test/right/fingers/5/");
		
		GroupTest ptt = new GroupTest();
		ptt.networkName = "OpenCloseRightHand";
		ptt.dirs = dirs;
		
		testNewInstances(ptt);
	}
	
	
	static String trainedNetworks = "trained_networks/";
	
	public static TrainingSet trainingSetGenerator(List<String> dirs)
	{
		TrainingSet ts = null;
		
		int size = dirs.size();
		double outputs[][] = new double[size][];
		
		for(int j = 0 ; j < size ; j++)
		{
			outputs[j] = new double[size];
			for(int k = 0 ; k < size ; k++)
			{
				outputs[j][k] = (k == j) ? 1.0 : 0.0;
			}
		}
		
		int i = 0;
		for(String dir : dirs)
		{
			String path = "output/" + dir;
			File actual = new File(path);
			
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
	
	
	public static void createAndTrainNetwork(GroupTrain p, int[] numHiddenNeurons)
	{
		List<String> dirs = p.dirs;
		String networkName = p.networkName;
		TrainingParameters tp = p.tp;
		
		TrainingSet ts = trainingSetGenerator(dirs);
		
		int[] structure = new int[numHiddenNeurons.length + 2];
		structure[0] = ts.getNumberOfInputs();
		System.arraycopy(numHiddenNeurons, 0, structure, 1, numHiddenNeurons.length);
		structure[structure.length - 1] = ts.getNumberOfOutputs();
		NeuralNetwork nn = new NeuralNetwork(structure);
		
		System.out.println("Training network...");
		trainNeuralNetwork(nn, ts, tp, true);
		
        //System.out.println("Serializing neural network...");
		try
		{
			File file = new File(trainedNetworks + networkName + ".nn");
			nn.serialize(file);
		}
		catch(Exception e){ e.printStackTrace(); }
	}
	
	public static void testNewInstances(GroupTest p)
	{
		String networkName = p.networkName;
		List<String> dirs = p.dirs;
		
		NeuralNetwork nn = null;
		
		//System.out.println("Unserializing...");
		try
		{
			File file = new File(trainedNetworks + networkName + ".nn");
			nn = NeuralNetwork.unserialize(file);
		}
		catch(Exception e){ e.printStackTrace(); }
		
		System.out.println("Testing new instances...");
		for(String dir : dirs)
		{
			System.out.println(dir + " :");;
			ShowOutput(nn, dir);
		}
	}
	
	
	private static void trainNeuralNetwork(NeuralNetwork nn, TrainingSet ts, TrainingParameters tp, boolean verbose)
	{
		int iterations;
		double error = 0;
		
		int iter = tp.numIterations/tp.errorChecks;
		for(iterations = 0 ; iterations < tp.errorChecks; iterations++)
		{
			nn.train(tp.lm, ts, iter, tp.learningRate, tp.momentum);
			
			error = nn.rootMeanSquaredError(ts);
			
			if(verbose) System.out.println(error);
			
			if(Math.abs(error) < tp.maxError) break;
		}
		
		if(verbose) System.out.println(iterations*iter + " " + error);
	}
	

	private static void ShowOutput(NeuralNetwork nn, String dir)
	{
		String path = "output/" + dir;
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
