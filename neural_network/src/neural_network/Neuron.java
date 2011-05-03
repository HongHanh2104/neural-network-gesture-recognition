package neural_network;

import util.Util;

public class Neuron implements IValue{
	
	private static final SimpleValue BIAS = new SimpleValue(-1);
	
	private IValue[] 	inputs;
	private float[]		weights;
	private float[]		deltaWeights;
	
	private float		delta;
	
	private float 		output;
	
	public Neuron(int numInputs) {
		if(numInputs < 1)
			throw new IllegalArgumentException("Perceptrons must have at least one input!");
		
		// Include the bias input!
		inputs = new IValue[numInputs + 1];
		weights = new float[numInputs + 1];
		deltaWeights = new float[numInputs + 1];
		
		for(int i = 0; i < numInputs + 1; i++) {
			inputs[i] = new SimpleValue(0);
			weights[i] = (float)(2 * Math.random() - 1);
			deltaWeights[i] = 0.0f;
		}
		
		inputs[0] = BIAS;
		
		delta = 0.0f;
		output = 0.0f;
	}

	@Override
	public float getValue() {
		return output;
	}
	
	@Override
	public String toString() {
		return Float.toString(output);
	}
	
	public void setInputs(IValue[] inputs) {
		if(inputs.length + 1 != this.inputs.length)
			throw new IllegalArgumentException("Input arrays lengths must be the same!");
		
		// Keep the bias input!!
		System.arraycopy(inputs, 0, this.inputs, 1, inputs.length);
	}
	
	private float activationFunction(float value) {
		return Util.sigmoid(value);
	}
	
	public void computeOutput() {
		output = activationFunction(Util.weightedSum(inputs, weights));
		delta = 0.0f;
	}
	
	public void setExpectedOutput(SimpleValue expectedOutput) {
		delta = expectedOutput.value - output;
	}
		
	public float deltaRule(float learningRate, float momentum) {
		
		float difference = delta;
		
		delta *= output * (1 - output);
		
		// Update weights
		for(int i = 0; i < weights.length; i++) {
			// Update delta in connected nodes before updating weights
			if(inputs[i] instanceof Neuron)
				((Neuron)inputs[i]).delta += weights[i] * delta;
			
			deltaWeights[i] *= momentum;
			deltaWeights[i] += learningRate * inputs[i].getValue() * delta;
			
			weights[i] += deltaWeights[i];
		}
		
		return difference * difference;
	}
}
