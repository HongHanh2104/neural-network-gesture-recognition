package neural_network;

import util.Util;

public class Neuron implements IValue{
	
	private IValue[] 	inputs;
	private float[]		weights;
	private float[]		deltaWeights;
	private float		threshold;
	private float 		deltaThreshold;
	
	private float		delta;
	
	private SimpleValue output;
	
	public Neuron(int numInputs) {
		if(numInputs < 1)
			throw new IllegalArgumentException("Perceptrons must have at least one input!");
		
		inputs = new SimpleValue[numInputs];
		weights = new float[numInputs];
		deltaWeights = new float[numInputs];
		
		for(int i = 0; i < numInputs; i++) {
			inputs[i] = new SimpleValue(0);
			weights[i] = 0.0f;
			deltaWeights[i] = 0.0f;
		}
		
		threshold = 0.0f;
		deltaThreshold = 0.0f;
		delta = 0.0f;
		output = new SimpleValue(0);
	}

	@Override
	public SimpleValue getValue() {
		return output;
	}
	
	@Override
	public String toString() {
		return output.toString();
	}
	
	public void setInputs(IValue[] inputs) {
		if(inputs.length != this.inputs.length)
			throw new IllegalArgumentException("Input arrays lengths must be the same!");
		
		this.inputs = inputs;
	}
	
	private float activationFunction(float value) {
		return Util.sigmoid(value);
	}
	
	public void computeOutput() {
		output.value = activationFunction(Util.dotProduct(inputs, weights) - threshold);
		delta = 0.0f;
	}
	
	public void setExpectedOutput(SimpleValue expectedOutput) {
		delta = expectedOutput.value - output.value;
	}
	
	public float deltaRule(float learningRate, float momentum) {
		
		float difference = delta;
		
		delta *= output.value * (1 - output.value);
		
		// Update threshold
		deltaThreshold *= momentum;
		deltaThreshold -= learningRate * delta;
		
		threshold += deltaThreshold;
		
		// Update weights
		for(int i = 0; i < weights.length; i++) {
			// Update delta in connected nodes before updating weights
			if(inputs[i] instanceof Neuron)
				((Neuron)inputs[i]).delta += weights[i] * delta;
			
			deltaWeights[i] *= momentum;
			deltaWeights[i] += learningRate * inputs[i].getValue().value * delta;
			
			weights[i] += deltaWeights[i];
		}
		
		return difference;
	}
}
