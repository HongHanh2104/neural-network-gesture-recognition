package main;

public class Util {

	public static float sigmoid(float x) 
	{
		return (float) (1/(1 + Math.pow(Math.E, -x)));
	}
}
