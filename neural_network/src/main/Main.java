package main;

import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) 
	{

		List<Input> inputs = new ArrayList<Input>();
		List<Weight> weights = new ArrayList<Weight>();
		
		inputs.add(new Input(1.1f));
		weights.add(new Weight(1.0f));
		
		inputs.add(new Input(4.02f));
		weights.add(new Weight(1.33f));
		
		
		Perceptron p = new Perceptron(inputs, weights);
		
		p.computeOutput();
		
		System.out.println("Valor computado por el perceptrón: " + p.getOutput().value);
		
	}

}
