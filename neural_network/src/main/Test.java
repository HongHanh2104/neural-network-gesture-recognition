package main;

import java.util.Arrays;

import neural_network.NeuralNetwork;
import neural_network.Neuron;
import neural_network.SimpleValue;

public class Test {
	
	public static enum TestToRun {NEURON, NEURALNETWORK};
	public static final TestToRun test = TestToRun.NEURALNETWORK;

	public static void main(String[] args) {
		
		switch(test) {
			case NEURON:
				nodeTest();
				break;
			case NEURALNETWORK:
				neuralNetworkTest();
				break;
		}
		
		System.out.println("Fin.");
	}
	
	private static void nodeTest() {
		Neuron perceptron = new Neuron(3);
		
		// Training set
		SimpleValue[][] training_set_inputs = {
			{new SimpleValue(0), new SimpleValue(0), new SimpleValue(0)},
			{new SimpleValue(1), new SimpleValue(0), new SimpleValue(0)},
			{new SimpleValue(0), new SimpleValue(1), new SimpleValue(0)},
			{new SimpleValue(1), new SimpleValue(1), new SimpleValue(0)},
			{new SimpleValue(0), new SimpleValue(0), new SimpleValue(1)},
			{new SimpleValue(1), new SimpleValue(0), new SimpleValue(1)},
			{new SimpleValue(0), new SimpleValue(1), new SimpleValue(1)},
			{new SimpleValue(1), new SimpleValue(1), new SimpleValue(1)}
		};
		
		SimpleValue[] training_set_outputs = {
				new SimpleValue(0),
				new SimpleValue(0),
				new SimpleValue(0),
				new SimpleValue(0),
				new SimpleValue(0),
				new SimpleValue(0),
				new SimpleValue(0),
				new SimpleValue(1)
		};
		
		// Train
		float learningRate = 0.5f;
		float momentum = 0.8f;
		float maxError = 0;
		
		int iterations = 0;
		do {
			maxError = Float.NEGATIVE_INFINITY;
			
			for(int i = 0; i < training_set_inputs.length; i++) {
				perceptron.setInputs(training_set_inputs[i]);
				perceptron.computeOutput();
				perceptron.setExpectedOutput(training_set_outputs[i]);
				maxError = Math.max(maxError, perceptron.deltaRule(learningRate, momentum));
			}
			
			System.out.println(maxError);
			iterations++;
		} while(Math.abs(maxError) > 0.01);
		
		System.out.println(iterations);
		System.out.println();
		
		// Test
		for(int i = 0; i < training_set_inputs.length; i++) {
			perceptron.setInputs(training_set_inputs[i]);
			perceptron.computeOutput();
			System.out.println(Arrays.toString(training_set_inputs[i]) + "\t" + perceptron.getValue().value + "\t" + (perceptron.getValue().value > 0.5 ? 1 : 0));
		}
	}
	
	public static void neuralNetworkTest() {
		
		// Two inputs, two nodes in hidden layer, one output
		NeuralNetwork nn = new NeuralNetwork(new int[]{2, 2, 1});
		
		SimpleValue[][] training_set_inputs = {
			{new SimpleValue(0), new SimpleValue(0)},
			{new SimpleValue(1), new SimpleValue(0)},
			{new SimpleValue(0), new SimpleValue(1)},
			{new SimpleValue(1), new SimpleValue(1)},
		};
		
		SimpleValue[][] training_set_outputs = {
				{new SimpleValue(0)},
				{new SimpleValue(0)},
				{new SimpleValue(0)},
				{new SimpleValue(1)}
		};
		
		// Train
		float learningRate = 0.01f;
		float momentum = 0f;
		float maxError = 0;
		
		int iterations = 0;
		
		do {
			maxError = Float.NEGATIVE_INFINITY;
			
			for(int i = 0; i < training_set_inputs.length; i++) {
				nn.setInputs(training_set_inputs[i]);
				nn.computeOutput();
				nn.setExpectedOutput(training_set_outputs[i]);
				maxError = Math.max(maxError, nn.backPropagation(learningRate, momentum));
			}
			
			System.out.println(maxError);
			iterations++;
		}while(Math.abs(maxError) > 0.1 || iterations < 100000);
		
		System.out.println(iterations);
		System.out.println();
		
		// Test
		for(int i = 0; i < training_set_inputs.length; i++) {
			nn.setInputs(training_set_inputs[i]);
			nn.computeOutput();
			System.out.println(Arrays.toString(training_set_inputs[i]) + "\t" + Arrays.toString(nn.getOutput()));
		}
	}
}
