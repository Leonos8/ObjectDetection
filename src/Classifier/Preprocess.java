package Classifier;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class Preprocess 
{
	Mat[] matrix;
	
	public Preprocess(Mat[] matrix)
	{
		this.matrix=matrix;
		for(int i=0; i<matrix.length; i++)
		{
			setMatrix(gammaCorrection(matrix[i]), i);
		}
	}
	
	public void setMatrix(Mat matrix, int i)
	{
		this.matrix[i]=matrix;
	}
	
	public Mat[] getMatrix()
	{
		return matrix;
	}
	
	public Mat gammaCorrection(Mat matrix)
	{		
		double gamma=2.2;
		
		for(int c=0; c<matrix.cols(); c++)
		{
			for(int r=0; r<matrix.rows(); r++)
			{
				double B=Math.pow((matrix.get(r, c)[0]/255), gamma)*255;
				double G=Math.pow((matrix.get(r, c)[1]/255), gamma)*255;
				double R=Math.pow((matrix.get(r, c)[2]/255), gamma)*255;
				
				matrix.put(r, c, new double[] {B, G, R});
			}
		}
		
		Imgproc.resize(matrix, matrix, new Size(64, 128));
		
		//image.displayImage(matrix);
		
		return matrix;
	}
}
