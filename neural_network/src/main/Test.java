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
		float totalError = 0;
		
		int iterations = 0;
		do {
			totalError = 0;
			
			for(int i = 0; i < training_set_inputs.length; i++) {
				perceptron.setInputs(training_set_inputs[i]);
				perceptron.computeOutput();
				perceptron.setExpectedOutput(training_set_outputs[i]);
				totalError += perceptron.deltaRule(learningRate, momentum);
			}
			
			System.out.println(totalError);
			iterations++;
		} while(Math.abs(totalError) > 0.0001);
		
		System.out.println(iterations);
		System.out.println();
		
		// Test
		for(int i = 0; i < training_set_inputs.length; i++) {
			perceptron.setInputs(training_set_inputs[i]);
			perceptron.computeOutput();
			System.out.println(Arrays.toString(training_set_inputs[i]) + "\t" + perceptron.getValue() + "\t" + (perceptron.getValue() > 0.5 ? 1 : 0));
		}
	}
	
	private static void neuralNetworkTest() {
		
		NeuralNetwork nn = new NeuralNetwork(new int[]{2, 2, 1});
		
		SimpleValue[][] training_set_inputs = {
			{new SimpleValue(0), new SimpleValue(0)},
			{new SimpleValue(1), new SimpleValue(0)},
			{new SimpleValue(0), new SimpleValue(1)},
			{new SimpleValue(1), new SimpleValue(1)},
		};
		
		SimpleValue[][] training_set_outputs = {
				{new SimpleValue(0)},
				{new SimpleValue(1)},
				{new SimpleValue(1)},
				{new SimpleValue(0)}
		};
		
		// Train
		float learningRate = 0.5f;
		float momentum = 0.9f;
		float totalError = 0;
		
		int iterations = 0;
		do {
			totalError = 0;
			
			for(int i = 0; i < training_set_inputs.length; i++) {
				nn.setInputs(training_set_inputs[i]);
				nn.computeOutput();
				nn.setExpectedOutput(training_set_outputs[i]);
				totalError += nn.backPropagation(learningRate, momentum);
			}
			
			System.out.println(totalError);
			iterations++;
		} while(Math.abs(totalError) > 0.0001);
		
		System.out.println(iterations + " " + totalError);
		
		// Test
		for(int i = 0; i < training_set_inputs.length; i++) {
			nn.setInputs(training_set_inputs[i]);
			nn.computeOutput();
			System.out.println(Arrays.toString(training_set_inputs[i]) + "\t" + Arrays.toString(nn.getOutput()));
		}
	}
}
