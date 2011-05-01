package training;

import java.util.Set;

public class TrainingSet {

	Set<TrainingInstance> trainingInstances;
	
	public TrainingSet(Set<TrainingInstance> trainingInstances)
	{
		this.trainingInstances = trainingInstances;
	}
	
	public Set<TrainingInstance> getTrainingInstances()
	{
		return trainingInstances;
	}
}
