package neural_network;

import training.ITrainingInstance;
import training.TrainingSet;


public class NeuralNetwork
{
	public static enum LearningMethod {INCREMENTAL, BATCH}
	
	private SimpleValue[] 	inputs;
	private Neuron[][] 		neurons;
	private Neuron[]		outputs;
	
	private TrainingSet 	trainingSet = null;
	
	public NeuralNetwork(int[] structure)
	{
		if(structure.length < 2)
			throw new IllegalArgumentException("The network must have at least two layers!");
		
		neurons = new Neuron[structure.length - 1][];
		
		// Create layers
		for(int layer = 0; layer < neurons.length; layer++)
		{
			neurons[layer] = new Neuron[structure[layer + 1]];
			
			// Create neurons in layer
			int numInputs = structure[layer];
			for(int neuron = 0; neuron < neurons[layer].length; neuron++)
				neurons[layer][neuron] = new Neuron(numInputs);
		}
		
		outputs = neurons[neurons.length - 1];
		
		// Connect layers
		for(int layer = 1; 	layer  < neurons.length; 		layer++ )
		for(int neuron = 0; neuron < neurons[layer].length; neuron++)
			neurons[layer][neuron].setInputs(neurons[layer - 1]);
		
		// Set inputs
		inputs = new SimpleValue[structure[0]];
		for(int i = 0; i < inputs.length; i++)
			inputs[i] = new SimpleValue(0);
		
		this.setInputs(inputs);
	}
	
	public void setInputs(SimpleValue[] inputs)
	{
		if(inputs.length != this.inputs.length)
			throw new IllegalArgumentException("Input arrays lengths must be the same!");
		
		this.inputs = inputs;
		
		for(Neuron neuron : neurons[0])
			neuron.setInputs(inputs);
		
		// Compute output for all neurons (forward propagation)
		for(int layer = 0; 	layer  < neurons.length; 		layer++ )
		for(int neuron = 0; neuron < neurons[layer].length; neuron++)
			neurons[layer][neuron].computeOutput();
	}
	
	public IValue[] getOutput()
	{
		return outputs;
	}
	
	public void setTrainingSet(TrainingSet trainingSet)
	{
		if(trainingSet.getNumberOfInputs() != inputs.length)
			throw new IllegalArgumentException("Training set's inputs must be the same as the network's inputs!");
		if(trainingSet.getNumberOfOutputs() != outputs.length)
			throw new IllegalArgumentException("Training set's outputs must be the same as the network's outputs!");
		
		this.trainingSet = trainingSet;
	}
	
	public void reset()
	{
		for(int layer = 0; 	layer  < neurons.length; 		layer++ )
		for(int neuron = 0; neuron < neurons[layer].length; neuron++)
			neurons[layer][neuron].resetWeights();
	}
	
	public double train(LearningMethod method, int iterations, double learningRate, double momentum)
	{
		if(this.trainingSet == null || this.trainingSet.getNumberOfInstances() == 0)
			throw new RuntimeException("Can't train the neural network without a non-empty training set!");
		
		double error = 0;
		
		while(iterations-- > 0)
		{
			error = 0;
			
			for(ITrainingInstance instance : trainingSet)
			{
				this.resetDeltas();
					
				// Set inputs
				this.setInputs(instance.getInputs());
				
				// Set expected outputs
				for(int neuron = 0; neuron < outputs.length; neuron++)
					error += outputs[neuron].setExpectedOutput(instance.getOutputs()[neuron]);
				
				// Backpropagation of delta
				for(int layer = neurons.length - 1; 	layer >= 0;						layer-- )
				for(int neuron = 0; 					neuron < neurons[layer].length; neuron++)
					neurons[layer][neuron].backPropagation();
				
				// Update weights if method is INCREMENTAL
				if(method == LearningMethod.INCREMENTAL)
					this.updateWeights(learningRate, momentum);
			}
			
			// Update weights if method is BATCH
			if(method == LearningMethod.BATCH)
				this.updateWeights(learningRate, momentum);
		}
		
		return error;
	}
	
	private void resetDeltas()
	{
		for(int layer = 0; 	layer  < neurons.length; 		layer++ )
		for(int neuron = 0; neuron < neurons[layer].length; neuron++)
			neurons[layer][neuron].resetDelta();
	}
	
	private void updateWeights(double learningRate, double momentum)
	{
		for(int layer = 0; 	layer  < neurons.length; 		layer++ )
		for(int neuron = 0; neuron < neurons[layer].length; neuron++)
			neurons[layer][neuron].updateWeights(learningRate, momentum);
	}
}
