package training;

import java.util.List;

import main.Input;
import main.Output;

public class TrainingInstance {

	List<Input> inputs;
	Output output;
	
	
	public TrainingInstance(List<Input> inputs, Output output)
	{
		this.inputs = inputs;
		this.output = output;
	}
	
	public List<Input> getInputs()
	{
		return inputs;
	}
	
	public Output getOutput()
	{
		return output;
	}
	
}
