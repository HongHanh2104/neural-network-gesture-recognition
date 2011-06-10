package neural_network;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import training.ITrainingInstance;
import training.TrainingSet;

public class NeuralNetwork implements Serializable
{
	private static final long serialVersionUID = 2833318363317164695L;

	public static enum LearningMethod {INCREMENTAL, BATCH}
	
	private SimpleValue[] 	inputs;
	private Neuron[][] 		neurons;
	private Neuron[]		outputs;
	
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
		
		for(Neuron neuron : neurons[0])
			neuron.setInputs(inputs);
	}
	
	public void serialize(File file) throws Exception
	{
		FileOutputStream fout = new FileOutputStream(file);
		ObjectOutputStream oos = new ObjectOutputStream(fout);
		oos.writeObject(this);
		oos.close();
		fout.close();
	}
	
	public static NeuralNetwork unserialize(File file) throws Exception 
	{
		FileInputStream fin = new FileInputStream(file);
		ObjectInputStream ois = new ObjectInputStream(fin);
		NeuralNetwork nn = (NeuralNetwork)ois.readObject();
		ois.close();
		fin.close();
		
		return nn;
	}
	
	public int getNumberOfInputs()
	{
		return inputs.length;
	}
	
	public int getNumberOfOutputs()
	{
		return outputs.length;
	}
	
	public void setInputs(SimpleValue[] inputs)
	{
		if(inputs.length != this.inputs.length)
			throw new IllegalArgumentException("Input arrays lengths must be the same!");
		
		for(int i = 0; i < this.inputs.length; i++)
			this.inputs[i].value = inputs[i].value;
		
		// Compute output for all neurons (forward propagation)
		for(int layer = 0; 	layer  < neurons.length; 		layer++ )
		for(int neuron = 0; neuron < neurons[layer].length; neuron++)
			neurons[layer][neuron].computeOutput();
	}
	
	public IValue[] getOutput()
	{
		return outputs;
	}
	
	public void reset()
	{
		for(int layer = 0; 	layer  < neurons.length; 		layer++ )
		for(int neuron = 0; neuron < neurons[layer].length; neuron++)
			neurons[layer][neuron].resetWeights();
	}
	
	public void train(LearningMethod method, TrainingSet trainingSet, int iterations, double learningRate, double momentum)
	{
		checkValidTrainingSet(trainingSet);
		
		while(iterations-- > 0)
		{			
			for(ITrainingInstance instance : trainingSet)
			{
				// Reset deltas
				for(int layer = 0; 	layer  < neurons.length; 		layer++ )
				for(int neuron = 0; neuron < neurons[layer].length; neuron++)
					neurons[layer][neuron].resetDelta();
					
				// Set inputs
				this.setInputs(instance.getInputs());
				
				// Set expected outputs
				for(int neuron = 0; neuron < outputs.length; neuron++)
					outputs[neuron].setExpectedOutput(instance.getOutputs()[neuron]);
				
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
	}
	
	public double rootMeanSquaredError(TrainingSet trainingSet)
	{
		checkValidTrainingSet(trainingSet);
		
		double error = 0;
		
		for(ITrainingInstance instance : trainingSet)
		{
			// Set inputs
			this.setInputs(instance.getInputs());
			
			// Get error from expected outputs
			for(int neuron = 0; neuron < outputs.length; neuron++)
				error += outputs[neuron].setExpectedOutput(instance.getOutputs()[neuron]);
		}
		
		return Math.sqrt(error / trainingSet.getNumberOfInstances());
	}
	
	private void checkValidTrainingSet(TrainingSet trainingSet)
	{
		if(trainingSet == null || trainingSet.getNumberOfInstances() == 0)
			throw new IllegalArgumentException("Can't train the neural network without a non-empty training set!");
		if(trainingSet.getNumberOfInputs() != inputs.length)
			throw new IllegalArgumentException("Training set's inputs must be the same as the network's inputs!");
		if(trainingSet.getNumberOfOutputs() != outputs.length)
			throw new IllegalArgumentException("Training set's outputs must be the same as the network's outputs!");
	}
	
	private void updateWeights(double learningRate, double momentum)
	{
		for(int layer = 0; 	layer  < neurons.length; 		layer++ )
		for(int neuron = 0; neuron < neurons[layer].length; neuron++)
			neurons[layer][neuron].updateWeights(learningRate, momentum);
	}
}
