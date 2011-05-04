package training;

import neural_network.SimpleValue;

public interface ITrainingInstance
{
	public SimpleValue[] getInputs();
	public SimpleValue[] getOutputs();
}
