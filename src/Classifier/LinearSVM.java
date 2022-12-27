package Classifier;

public class LinearSVM 
{
	double[][] positiveFeatureVector;
	double[][] negativeFeatureVector;
	double lr;
	int numOfFeatures;
	int c;
	int epochs;
	
	double[][][] featureVector;
	
	public LinearSVM(double[][] positiveFeatureVector, double[][] negativeFeatureVector, double lr, int c, int epochs)
	{
		this.positiveFeatureVector=positiveFeatureVector;
		this.negativeFeatureVector=negativeFeatureVector;
		this.lr=lr;
		this.epochs=epochs;
		this.numOfFeatures=positiveFeatureVector[0].length;
		this.c=c;
		
		this.featureVector=new double[2][positiveFeatureVector.length+negativeFeatureVector.length]
				[positiveFeatureVector[0].length];
		
		
		for(int i=0; i<positiveFeatureVector.length+negativeFeatureVector.length; i++)
		{
			if(i<positiveFeatureVector.length)
			{
				featureVector[0][i][0]=1;
				featureVector[1][i]=positiveFeatureVector[i];
			}
			else if(i-positiveFeatureVector.length<negativeFeatureVector.length)
			{
				featureVector[0][i][0]=-1;
				featureVector[1][i]=negativeFeatureVector[i-positiveFeatureVector.length];
			}
		}
		
		//System.out.println(featureVector[1][1][0]);
	}
	
	public double distances(boolean withLagrange)
	{
		double distance = 0;
		
		for(int i=0; i<featureVector.length; i++)
		{
			distance=featureVector[0][0][0]*dotProduct()-1;
		}
		print(positiveFeatureVector);
		
		if(withLagrange)
		{
			
		}
		
		return distance;
	}
	
	public double dotProduct(double[] x, double [] y)
	{
		double dot=0;
		
		for(int i=0; i<x.length; i++)
		{
			dot+=x[i]*y[i];
		}
		
		return dot;
	}
	
	public void print(double[][] x)
	{
		for(int i=0; i<x.length; i++)
		{
			for(int j=0; j<x[i].length; j++)
			{
				System.out.print(x[i][j]+" ");
			}
			System.out.println();
		}
	}
	
	public void train()
	{
		
	}
	
	
	
	/*public void linearKernel()
	{
		int[][] a=new int[][]{{1, 0}, {0,1}};
		int[][] b=new int[][]{{4, 1}, {2,2}};
		
		int[][] cross=new int[2][2];
		
		cross[0][0] = a[0][1] * b[0][2]
                - a[0][2] * b[0][1];
		cross[0][1] = a[0][2] * vect_B[0]
                - vect_A[0] * vect_B[2];
		//cross_P[2] = vect_A[0] * vect_B[1]
               // - vect_A[1] * vect_B[0];
	}*/
}
