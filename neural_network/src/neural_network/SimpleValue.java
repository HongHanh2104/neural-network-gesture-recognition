package neural_network;

public class SimpleValue implements IValue
{
	public double value;
	
	public SimpleValue(double value)
	{
		this.value = value;
	}

	@Override
	public double getValue()
	{
		return value;
	}
	
	@Override
	public String toString()
	{
		return Double.toString(value);
	}

}
