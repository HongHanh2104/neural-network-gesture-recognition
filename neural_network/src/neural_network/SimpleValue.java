package neural_network;

public class SimpleValue implements IValue{

	public float value;
	
	public SimpleValue(float value) {
		this.value = value;
	}

	@Override
	public float getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		return Float.toString(value);
	}

}
