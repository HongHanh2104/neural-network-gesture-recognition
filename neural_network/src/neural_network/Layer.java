package neural_network;

public class Layer {

	protected Value[] inputs;
	protected Perceptron[] perceptrons;
	private Value[] outputs;
	
	public Layer(int numInputs, int numPerceptrons) {
		
		perceptrons = new Perceptron[numPerceptrons];
		for(int i = 0; i < numPerceptrons; i++)
			perceptrons[i] = new Perceptron(numInputs);
			
		inputs = new Value[numInputs];
		for(int i = 0; i < numInputs; i++)
			inputs[i] = new Value(0);
		
		for(Perceptron perceptron : perceptrons)
			perceptron.setInputs(inputs);
		
		outputs = new Value[numPerceptrons];
		for(int i = 0; i < numPerceptrons; i++)
			outputs[i] = perceptrons[i].getOutput();
	}
	
	public int getNumInputs() {
		return inputs.length;
	}
	
	public int getNumOutputs() {
		return outputs.length;
	}
	
	// Connects this Layer outputs to the other Layer's inputs
	public void connectTo(Layer other) {
		if(this.getNumOutputs() != other.getNumInputs())
			throw new IllegalArgumentException("Can't connect layers with different number of connections!");
		
		for(Perceptron perceptron : other.perceptrons)
			perceptron.setInputs(outputs);
		
		other.inputs = null;
	}
	
	public void computeOutput() {
		for(Perceptron perceptron : perceptrons)
			perceptron.computeOutput();
	}
}
