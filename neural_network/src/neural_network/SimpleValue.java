package neural_network;

import java.io.Serializable;

public class SimpleValue implements IValue, Serializable
{
	private static final long serialVersionUID = 7661063626244223642L;
	
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
