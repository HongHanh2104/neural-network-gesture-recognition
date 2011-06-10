package neural_network;

import java.io.Serializable;

public class Neuron implements IValue, Serializable
{
	private static final long serialVersionUID = 7687714747936452242L;

	private static final SimpleValue BIAS = new SimpleValue(-1);
	
	private IValue[] 	inputs;
	private double[]	weights;
	private double[]	deltaWeights;
	
	private double		delta;
	
	private double 		output;
	
	public Neuron(int numInputs)
	{
		if(numInputs < 1)
			throw new IllegalArgumentException("Neurons must have at least one input!");
		
		// Include the bias input!
		inputs = new IValue[numInputs + 1];
		weights = new double[numInputs + 1];
		deltaWeights = new double[numInputs + 1];
		
		for(int i = 0; i < numInputs + 1; i++)
			inputs[i] = new SimpleValue(0);
		
		this.resetWeights();
		
		inputs[0] = BIAS;
		
		delta = 0;
		output = 0;
	}

	@Override
	public double getValue()
	{
		return output;
	}
	
	@Override
	public String toString()
	{
		return Double.toString(output);
	}
	
	public void setInputs(IValue[] inputs)
	{
		if(inputs.length + 1 != this.inputs.length)
			throw new IllegalArgumentException("Input arrays lengths must be the same!");
		
		// Keep the bias input!!
		System.arraycopy(inputs, 0, this.inputs, 1, inputs.length);
	}
	
	public void computeOutput()
	{
		double sum = 0.0f;
		
		for(int i = 0; i < inputs.length; i++)
			sum += inputs[i].getValue() * weights[i];
		
		output = (1 / (1 + Math.exp(-sum)));
	}
	
	public void resetDelta()
	{
		delta = 0;
	}
	
	public double setExpectedOutput(SimpleValue expectedOutput)
	{
		delta = expectedOutput.value - output;
		return delta * delta;
	}
	
	public void backPropagation()
	{
		double derivative = output * (1 - output);
		
		for(int i = 0; i < inputs.length; i++)
		{
			if(inputs[i] instanceof Neuron)
				((Neuron)inputs[i]).delta += weights[i] * delta;
			
			deltaWeights[i] += derivative * inputs[i].getValue() * delta;
		}
	}
	
	public void updateWeights(double learningRate, double momentum)
	{
		for(int i = 0; i < weights.length; i++)
		{		
			weights[i] += learningRate * deltaWeights[i];
			
			deltaWeights[i] *= momentum;
		}
	}
	
	public void resetWeights()
	{
		for(int i = 0; i < weights.length; i++)
		{
			weights[i] = 2 * Math.random() - 1;
			deltaWeights[i] = 0;
		}
	}
}
