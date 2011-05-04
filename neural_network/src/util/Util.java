package util;

import neural_network.IValue;

public class Util
{
	public static double sigmoid(double x)
	{
		return (1 / (1 + Math.exp(-x)));
	}
	
	public static double weightedSum(IValue[] array, double[] weights)
	{
		if(array.length != weights.length)
			throw new IllegalArgumentException("Input arrays do not have the same lengths!");
		
		double output = 0.0f;
	
		for(int i = 0; i < array.length; i++)
			output += array[i].getValue() * weights[i];
		
		return output;
	}
}
