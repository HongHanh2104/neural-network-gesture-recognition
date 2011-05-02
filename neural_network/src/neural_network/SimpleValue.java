package neural_network;

public class SimpleValue implements IValue{

	public float value;
	
	public SimpleValue(float value) {
		this.value = value;
	}

	@Override
	public SimpleValue getValue() {
		return this;
	}
	
	@Override
	public String toString() {
		return Float.toString(value);
	}

}
