package main;

import neural_network.Perceptron;
import neural_network.Value;

public class Test {

	public static void main(String[] args) {

		Perceptron p = new Perceptron(4);
		
		// Training set
		Value[][] training_set_inputs = {
			{new Value(-1), new Value(0), new Value(0), new Value(0)},
			{new Value(-1), new Value(1), new Value(0), new Value(0)},
			{new Value(-1), new Value(0), new Value(1), new Value(0)},
			{new Value(-1), new Value(1), new Value(1), new Value(0)},
			{new Value(-1), new Value(0), new Value(0), new Value(1)},
			{new Value(-1), new Value(1), new Value(0), new Value(1)},
			{new Value(-1), new Value(0), new Value(1), new Value(1)},
			{new Value(-1), new Value(1), new Value(1), new Value(1)}
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
				totalError += p.deltaRule(training_set_inputs[i], training_set_outputs[i], learningRate);
			
			iterations++;
		} while(Math.abs(totalError) > 0.1);
		
		System.out.println(iterations);
		System.out.println();
		
		// Test
		for(int i = 0; i < training_set_inputs.length; i++) {
			p.setInputs(training_set_inputs[i]);
			p.computeOutput();
			System.out.println(p.getOutput().value > 0.5 ? 1 : 0);
		}
		
	}

}
