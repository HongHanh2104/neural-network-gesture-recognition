package neural_network;

public class NeuralNetwork {
	
	private SimpleValue[] 	inputs;
	private Neuron[][] 		neurons;
	private Neuron[] 		lastLayer;
	
	public NeuralNetwork(int[] structure) {
		if(structure.length < 2)
			throw new IllegalArgumentException("The network must have at least two layers!");
		
		neurons = new Neuron[structure.length - 1][];
		
		// Create layers
		for(int layer = 0; layer < neurons.length; layer++) {
			neurons[layer] = new Neuron[structure[layer + 1]];
			
			// Create neurons in layer
			int numInputs = structure[layer];
			for(int neuron = 0; neuron < neurons[layer].length; neuron++) {
				neurons[layer][neuron] = new Neuron(numInputs);
			}
		}
		
		lastLayer = neurons[neurons.length - 1];
		
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
	
	public void setInputs(SimpleValue[] inputs) {
		if(inputs.length != this.inputs.length)
			throw new IllegalArgumentException("Input arrays lengths must be the same!");
		
		this.inputs = inputs;
		
		for(Neuron neuron : neurons[0])
			neuron.setInputs(inputs);
	}
	
	public void computeOutput() {
		for(int layer = 0; 	layer  < neurons.length; 		layer++ )
		for(int neuron = 0; neuron < neurons[layer].length; neuron++)
			neurons[layer][neuron].computeOutput();
	}
	
	public IValue[] getOutput() {
		return neurons[neurons.length - 1];
	}
	
	public void setExpectedOutput(SimpleValue[] expectedOutput) {
		if(expectedOutput.length != lastLayer.length)
			throw new IllegalArgumentException("The expected output does not have the same size as the output!");
		
		for(int neuron = 0; neuron < lastLayer.length; neuron++)
			lastLayer[neuron].setExpectedOutput(expectedOutput[neuron]);
	}
	
	public float backPropagation(float learningRate, float momentum) {
		float lastLayerError = 0.0f;
		float error;
		
		for(int layer = neurons.length - 1; 	layer >= 0; 					layer-- )
		for(int neuron = 0; 					neuron < neurons[layer].length; neuron++) {
			error = neurons[layer][neuron].deltaRule(learningRate, momentum);
			
			if(neurons[layer] == lastLayer) lastLayerError += error;
		}
		
		return lastLayerError;
	}
}
