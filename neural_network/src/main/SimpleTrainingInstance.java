package main;

import java.util.Arrays;

import training.ITrainingInstance;
import neural_network.SimpleValue;

public class SimpleTrainingInstance implements ITrainingInstance
{
	private SimpleValue[] inputs;
	private SimpleValue[] outputs;
	
	public SimpleTrainingInstance(double[] inputs, double[] outputs)
	{
		this.inputs = new SimpleValue[inputs.length];
		for(int i = 0; i < this.inputs.length; i++)
			this.inputs[i] = new SimpleValue(inputs[i]);
		
		this.outputs = new SimpleValue[outputs.length];
		for(int i = 0; i < this.outputs.length; i++)
			this.outputs[i] = new SimpleValue(outputs[i]);
	}

	@Override
	public SimpleValue[] getInputs()
	{
		return inputs;
	}

	@Override
	public SimpleValue[] getOutputs()
	{
		return outputs;
	}
	
	public String toString()
	{
		return Arrays.toString(inputs) + " -> " + Arrays.toString(outputs);
	}
}
