package main;

import neural_network.Layer;
import neural_network.Perceptron;
import neural_network.Value;

public class Test {
	
	public static enum TestToRun {PERCEPTRON, LAYER};
	public static final TestToRun test = TestToRun.PERCEPTRON;

	public static void main(String[] args) {
		
		switch(test) {
			case PERCEPTRON:
				perceptronTest();
				break;
			case LAYER:
				layerTest();
				break;
		}
		
		System.out.println("Fin.");
	}
	
	private static void perceptronTest() {
		Perceptron perceptron = new Perceptron(3);
		
		// Training set
		Value[][] training_set_inputs = {
			{new Value(0), new Value(0), new Value(0)},
			{new Value(1), new Value(0), new Value(0)},
			{new Value(0), new Value(1), new Value(0)},
			{new Value(1), new Value(1), new Value(0)},
			{new Value(0), new Value(0), new Value(1)},
			{new Value(1), new Value(0), new Value(1)},
			{new Value(0), new Value(1), new Value(1)},
			{new Value(1), new Value(1), new Value(1)}
		};
		
		Value[] training_set_outputs = {
				new Value(0),
				new Value(0),
				new Value(0),
				new Value(0),
				new Value(0),
				new Value(0),
				new Value(0),
				new Value(1)
		};
		
		// Train
		float learningRate = 0.01f;
		float totalError = 0;
		
		int iterations = 0;
		do {
			totalError = 0;
			
			for(int i = 0; i < training_set_inputs.length; i++)
				totalError += perceptron.deltaRule(training_set_inputs[i], training_set_outputs[i], learningRate);
			
			iterations++;
		} while(Math.abs(totalError) > 0.1);
		
		System.out.println(iterations);
		System.out.println();
		
		// Test
		for(int i = 0; i < training_set_inputs.length; i++) {
			perceptron.setInputs(training_set_inputs[i]);
			perceptron.computeOutput();
			System.out.println(perceptron.getOutput().value > 0.5 ? 1 : 0);
		}
	}
	
	private static void layerTest() {
		Layer layer1 = new Layer(4, 10);
		Layer layer2 = new Layer(10, 7);
		
		layer1.connectTo(layer2);
		
		layer1.computeOutput();
		layer2.computeOutput();
	}
	
}
