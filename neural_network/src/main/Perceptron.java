package main;

import java.util.List;


public class Perceptron {

	List<Input> inputs;
	List<Weight> weights;
	
	Output output;

	public Perceptron(List<Input> inputs, List<Weight> weights)
	{
		assert(inputs != null && weights != null);
		assert(inputs.size() == weights.size());
		
		setInputs(inputs);
		setWeights(weights);
		initOutput();
	}
	
	public void initOutput()
	{
		this.output = new Output();
	}
	
	public void setWeights(List<Weight> weight)
	{
		this.weights = weight;
	}
	public void setInputs(List<Input> inputs)
	{
		this.inputs = inputs;
	}
	
	public Output getOutput()
	{
		return this.output;
	}
	
	public void computeOutput()
	{
		float v = 0.0f;
		
		int size = inputs.size();
		for(int i = 0 ; i < size ; i++)
		{
			v += inputs.get(i).value * weights.get(i).value;
		}
		
		this.output.value = activationFunction(v);
	}
	
	float activationFunction(float v)
	{
		return Util.sigmoid(v);
	}

}
