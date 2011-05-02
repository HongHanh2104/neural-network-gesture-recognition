package util;

import neural_network.IValue;

public class Util {

	public static float sigmoid(float x) {
		return (float)(1 / (1 + Math.exp(-x)));
	}
	
	public static float dotProduct(IValue[] inputs, float[] weights) {
	
		if(inputs.length != weights.length)
			throw new IllegalArgumentException("Input arrays do not have the same length!");
		
		float output = 0.0f;
	
		for(int i = 0; i < inputs.length; i++)
			output += inputs[i].getValue().value * weights[i];
		
		return output;
	}
}
