package util;

import neural_network.Value;

public class Util {

	public static float sigmoid(float x) {
		return (float)(1 / (1 + Math.exp(-x)));
	}
	
	public static float dotProduct(Value[] array1, Value[] array2) {
	
		if(array1.length != array2.length)
			throw new IllegalArgumentException("Input arrays do not have the same length!");
		
		float output = 0.0f;
	
		for(int i = 0; i < array1.length; i++)
			output += array1[i].value * array2[i].value;
		
		return output;
	}
}
