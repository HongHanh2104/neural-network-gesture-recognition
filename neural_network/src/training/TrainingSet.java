package training;

import java.util.ArrayList;
import java.util.Iterator;

public class TrainingSet implements Iterable<ITrainingInstance>
{
	private int inputs;
	private int outputs;
	
	private ArrayList<ITrainingInstance> instances;
	
	public TrainingSet(int inputs, int outputs)
	{
		this.inputs = inputs;
		this.outputs = outputs;
		
		this.instances = new ArrayList<ITrainingInstance>();
	}
	
	public int getNumberOfInputs()
	{
		return inputs;
	}
	
	public int getNumberOfOutputs()
	{
		return outputs;
	}
	
	public void addInstance(ITrainingInstance newInstance)
	{
		if(newInstance.getInputs().length != inputs)
			throw new IllegalArgumentException("New instance's inputs must be the same as configured inputs!");
		if(newInstance.getOutputs().length != outputs)
			throw new IllegalArgumentException("New instance's outputs must be the same as configured outputs!");
		
		this.instances.add(newInstance);
	}
	
	public ITrainingInstance getInstance(int index)
	{
		return instances.get(index);
	}
	
	public int getNumberOfInstances()
	{
		return instances.size();
	}

	@Override
	public Iterator<ITrainingInstance> iterator()
	{
		return this.instances.iterator();
	}
}
