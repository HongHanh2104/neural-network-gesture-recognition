package training;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import main.Input;
import main.Output;
import main.Perceptron;
import main.Weight;

public class DeltaRuleTrainer 
{
	Perceptron p;
	Predicate<Perceptron> pred;
	TrainingSet trainingSet;
	
	float learningFactor;
	
	public DeltaRuleTrainer(Perceptron p, Predicate<Perceptron> pred, TrainingSet trainingSet, float learningFactor)
	{
		this.p = p;
		this.pred = pred;
		
		this.learningFactor = learningFactor;
	}
	
	
	private void initWeights() 
	{
		
		p.setWeights(weights);
	}
	
	private void updateWeights(List<Input> inputs, Output output) 
	{
		//wi  <- wi + η(y − o)xi
		
		int i = 0;
		for(Weight w : p.getWeights())
		{
			w.setValue(w.getValue() + learningFactor * (output.getValue() - p.getOutput().getValue()) * inputs.get(i).getValue());
			i++;
		}
	}
	
	
	public void run()
	{
		initWeights();
		
		while(pred.apply(p))
		{
			for(TrainingInstance ti : trainingSet.getTrainingInstances())
			{
				p.setInputs(ti.inputs);
				p.computeOutput();
				
				updateWeights(ti.inputs, ti.output);
			}
		}
	}




	
	
}
