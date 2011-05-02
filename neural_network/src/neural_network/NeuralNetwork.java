package neural_network;

public class NeuralNetwork {
	
	private Layer[] layers;
	
	public NeuralNetwork(int[] structure) {
		if(structure.length < 2)
			throw new IllegalArgumentException("The network must have at least two layers!");
		
		layers = new Layer[structure.length - 1];
		
		for(int i = 0; i < layers.length; i++)
			layers[i] = new Layer(structure[i], structure[i + 1]);
		
		for(int i = 0; i < layers.length - 1; i++)
			layers[i].connectTo(layers[i + 1]);
		
		SimpleValue[] inputs = new SimpleValue[structure[0]];
		for(int i = 0; i < inputs.length; i++)
			inputs[i] = new SimpleValue(0);
		
		setInputs(inputs);
	}
	
	public void setInputs(SimpleValue[] inputs) {
		if(inputs.length != layers[0].getNumInputs())
			throw new IllegalArgumentException("Input arrays lengths must be the same!");
		
		layers[0].setInputs(inputs);
	}
	
	public void computeOutput() {
		for(int i = 0; i < layers.length; i++)
			layers[i].computeOutput();
	}
	
	public IValue[] getOutput() {
		return layers[layers.length - 1].getOutput();
	}
	
	public void setExpectedOutput(SimpleValue[] expectedOutput) {
		layers[layers.length - 1].setExpectedOutput(expectedOutput);
	}
	
	public float backPropagation(float learningRate, float momentum) {
		float error = layers[layers.length - 1].deltaRule(learningRate, momentum);
			
		for(int i = layers.length - 2; i >= 0; i--)
				layers[i].deltaRule(learningRate, momentum);
		
		return error;
	}
}
