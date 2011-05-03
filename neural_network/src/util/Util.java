package util;

import neural_network.IValue;

public class Util {

	public static float sigmoid(float x) {
		return (float)(1 / (1 + Math.exp(-x)));
	}
	
	public static float weightedSum(IValue[] array, float[] weights) {
	
		if(array.length != weights.length)
			throw new IllegalArgumentException("Input arrays do not have the same lengths!");
		
		float output = 0.0f;
	
		for(int i = 0; i < array.length; i++)
			output += array[i].getValue() * weights[i];
		
		return output;
	}
}
