package neural_network;

public class Layer {

	private IValue[] 		inputs;
	private Neuron[] 			nodes;
	
	public Layer(int numInputs, int numNodes) {
		
		nodes = new Neuron[numNodes];
		for(int i = 0; i < numNodes; i++)
			nodes[i] = new Neuron(numInputs);
		
		inputs = new SimpleValue[numInputs];
		for(int i = 0; i < inputs.length; i++)
			inputs[i] = new SimpleValue(0);
		
		setInputs(inputs);
	}
	
	public int getNumInputs() {
		return inputs.length;
	}
	
	public void setInputs(IValue[] inputs) {
		if(inputs.length != this.inputs.length)
			throw new IllegalArgumentException("Input arrays lengths must be the same!");
		
		this.inputs = inputs;
		
		for(int i = 0; i < nodes.length; i++)
			nodes[i].setInputs(inputs);
	}
	
	// Connects this Layer outputs to the other Layer's inputs
	public void connectTo(Layer other) {
		if(this.nodes.length != other.inputs.length)
			throw new IllegalArgumentException("Can't connect layers with different number of connections!");
		
		other.setInputs(this.nodes);
	}
	
	public void computeOutput() {
		for(Neuron node : nodes)
			node.computeOutput();
	}
	
	public IValue[] getOutput() {
		return nodes;
	}
	
	public void setExpectedOutput(SimpleValue[] expectedOutput) {
		if(expectedOutput.length != nodes.length)
			throw new IllegalArgumentException("Expected output has a wrong size!");
		
		for(int i = 0; i < nodes.length; i++)
			nodes[i].setExpectedOutput(expectedOutput[i]);
	}
	
	public float deltaRule(float learningRate, float momentum) {
		float maxError = Float.NEGATIVE_INFINITY;
		
		for(Neuron node : nodes)
			maxError = Math.max(maxError, node.deltaRule(learningRate, momentum));
		
		return maxError;
	}
}
