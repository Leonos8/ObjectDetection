package Classifier;

public class LinearSVM 
{
	double[][] featureVector;
	int epochs;
	int batch;
	double learningRate;
	double C;
	
	public LinearSVM(double[][] featureVector, int epochs, int batch, double learningRate, double C)
	{
		this.featureVector=featureVector;
		this.epochs=epochs;
		this.batch=batch;
		this.learningRate=learningRate;
		this.C=C;
	}
}
