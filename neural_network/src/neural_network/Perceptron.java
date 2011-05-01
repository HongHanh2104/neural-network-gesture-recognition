package neural_network;

import util.Util;

public class Perceptron {

	private Value[] inputs;
	private Value[] weights;
	
	private Value output;
	
	public Perceptron(int numInputs) {
		inputs = new Value[numInputs];
		weights = new Value[numInputs];
		
		for(int i = 0; i < numInputs; i++)
			weights[i] = new Value(0);
		
		output = new Value(0);
	}
	
	public void setInput(int index, Value value) {
		inputs[index] = value;
	}
	
	public void setInputs(Value[] inputs) {
		if(inputs.length != this.inputs.length)
			throw new IllegalArgumentException("Input array does not have the same length as the inputs array!");
		
		this.inputs = inputs;
	}
	
	public Value getOutput() {
		return output;
	}
	
	private float activationFunction(float value) {
		return Util.sigmoid(value);
	}
	
	private float derivativeOfActivationFunction(float value) {
		float sigmoid = Util.sigmoid(value);
		return sigmoid * (1 - sigmoid);
	}
	
	public void computeOutput() {
		output.value = activationFunction(Util.dotProduct(inputs, weights));
	}
	
	public float deltaRule(Value[] targetInput, Value targetOutput, float learningRate) {
		if(targetInput.length != weights.length)
			throw new IllegalArgumentException("Input array does not have the same length as the weights array!");
		
		float in = Util.dotProduct(targetInput, weights);
		float difference = (targetOutput.value - activationFunction(in));
		
		float factor = learningRate * difference * derivativeOfActivationFunction(in);
		 
		for(int i = 0; i < weights.length; i++)
			weights[i].value += (float)(factor * targetInput[i].value);
		
		return difference;
	}
}
