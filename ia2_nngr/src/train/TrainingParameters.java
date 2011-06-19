package train;

import neural_network.NeuralNetwork;
import neural_network.NeuralNetwork.LearningMethod;

public class TrainingParameters {

	public double learningRate = 0.01;
	public double momentum = 0.2;
	public int numIterations = 10000;
	public int errorChecks = 50;
	public double maxError = 0.001;
	public LearningMethod learningMethod = NeuralNetwork.LearningMethod.BATCH;
	
}
