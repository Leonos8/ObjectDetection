package Image;

import java.util.ArrayList;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class FeatureExtraction 
{
	ImageCreation image=new ImageCreation();
	Preprocess processor=new Preprocess();
	Mat matrix=processor.getMatrix();
	
	public FeatureExtraction()
	{
		HistogramOfOrientedGradients(matrix);
	}
	
	public void HistogramOfOrientedGradients(Mat matrix)
	{
		Imgproc.cvtColor(matrix, matrix, Imgproc.COLOR_BGR2GRAY);
		
		image.displayImage(matrix);
		
		ArrayList<Double> Gx=new ArrayList<Double>(); //(r,c)
		ArrayList<Double> Gy=new ArrayList<Double>(); //(r,c)
		ArrayList<Double> magnitude=new ArrayList<Double>();
		ArrayList<Double> theta=new ArrayList<Double>();
		
		int i=0;
		for(int c=1; c<matrix.cols()-1; c++)
		{
			for(int r=1; r<matrix.rows()-1; r++)
			{
				Gx.add(matrix.get(r, c+1)[0]-matrix.get(r, c-1)[0]);
				Gy.add(matrix.get(r-1, c)[0]-matrix.get(r+1, c)[0]);
				
				magnitude.add(Math.sqrt(Math.pow(Gx.get(i), 2)+Math.pow(Gy.get(i), 2)));
				theta.add(Math.toDegrees(Math.atan(Gy.get(i)/Gx.get(i))));
				i++;
			}
		}
		
		for(int x=0; x<matrix.cols()-2; x++)
		{
			for(int j=0; j<matrix.rows()-2; j++)
			{
				matrix.put(x, j, magnitude.get(x+j));
			}
		}
		
		image.displayImage(matrix);
	}
}
