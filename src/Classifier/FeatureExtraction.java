package Classifier;

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
		
		double[][] Gx=new double[matrix.cols()][matrix.rows()];
		double[][] Gy=new double[matrix.cols()][matrix.rows()];
		double[][] magnitude=new double[matrix.cols()][matrix.rows()];
		double[][] theta=new double[matrix.cols()][matrix.rows()];
		
		int i=0;
		try
		{			
			for(int c=0; c<matrix.cols(); c++)
			{
				for(int r=0; r<matrix.rows(); r++)
				{
					if(c==0 || c==matrix.cols()-1 || r==0 || r==matrix.rows()-1)
					{
						if(c==0 && r==0)
						{
							Gx[c][r]=matrix.get(r, c+1)[0];
							Gy[c][r]=0-matrix.get(r+1, c)[0];
						}
						else if(c==0 && r==matrix.rows()-1)
						{
							Gx[c][r]=matrix.get(r, c+1)[0];
							Gy[c][r]=matrix.get(r-1, c)[0];
						}
						else if(c==matrix.cols()-1 && r==0)
						{
							Gx[c][r]=0-matrix.get(r, c-1)[0];
							Gy[c][r]=0-matrix.get(r+1, c)[0];
						}
						else if(c==matrix.cols()-1 && r==matrix.rows()-1)
						{
							Gx[c][r]=0-matrix.get(r, c-1)[0];
							Gy[c][r]=matrix.get(r-1, c)[0];
						}
						else if(c==0)
						{
							Gx[c][r]=matrix.get(r, c+1)[0];
							Gy[c][r]=matrix.get(r-1, c)[0]-matrix.get(r+1, c)[0];
						}
						else if(c==matrix.cols()-1)
						{
							Gx[c][r]=0-matrix.get(r, c-1)[0];
							Gy[c][r]=matrix.get(r-1, c)[0]-matrix.get(r+1, c)[0];
						}
						else if(r==0)
						{
							Gx[c][r]=matrix.get(r, c+1)[0]-matrix.get(r, c-1)[0];
							Gy[c][r]=0-matrix.get(r+1, c)[0];
						}
						else if(r==matrix.rows()-1)
						{
							Gx[c][r]=matrix.get(r, c+1)[0]-matrix.get(r, c-1)[0];
							Gy[c][r]=matrix.get(r-1, c)[0];
						}
					}
					else
					{
						Gx[c][r]=matrix.get(r, c+1)[0]-matrix.get(r, c-1)[0];
						Gy[c][r]=matrix.get(r-1, c)[0]-matrix.get(r+1, c)[0];
					}
					
					magnitude[c][r]=Math.sqrt(Math.pow(Gx[c][r], 2)+Math.pow(Gy[c][r], 2));
					theta[c][r]=Math.toDegrees(Math.atan2(Gx[c][r], Gy[c][r]));
					//theta[c][r]=Math.abs(Math.toDegrees(Math.atan(Gy[c][r]/Gx[c][r]))); TODO
					
					if(theta[c][r]<0)
					{
						theta[c][r]+=180;
					}
					
					i++;
				}
			}

		}catch(NullPointerException ex)
		{
			System.out.println("hi");
		}
		
		image.displayImage(matrix);
		
		int j=0;
		for(int c=0; c<matrix.cols(); c++)
		{
			for(int r=0; r<matrix.rows(); r++)
			{
				matrix.put(r, c, magnitude[c][r]);
				j++;
			}
		}
		
		image.displayImage(matrix);
		
		int binNum=9;
		int stepSize=20;
		
		double[][][] ninePointHistogram=calculate9PointHistogram(magnitude, theta, binNum, stepSize);
		createFeatureVector(ninePointHistogram);
	}
	
	public double[][][] calculate9PointHistogram(double[][] magnitude, double[][] theta, int binNum, int stepSize)
	{
		double[][][] ninePointHistogram=new double[16][8][9];
		
		for(int r=0; r<128; r++)
		{
			int histBlockRow = r/8;
			double[][] temp=new double[8][9];
			for(int c=0; c<64; c++)
			{
				double[] bins=new double[binNum];
				for(int t=0; t<binNum; t++)
				{
					bins[t]=0;
				}
				int histBlockCol = c/8;
				
				int b=(int)(theta[c][r]/20);
				double br=theta[c][r]%20;
						
				double v0=magnitude[c][r]*((20-br)/20);
				double v1=magnitude[c][r]*(br/20);
				
				if(b==0 && br==0)
				{
					bins[0]=v0+v1;
				}
				else if(b<8)
				{
					bins[b]+=v0;
					bins[b+1]+=v1;
				}
				else if(b==9)
				{
					bins[8]+=v0+v1;
				}
				else
				{
					bins[b]+=v0;
					bins[0]+=v1;
				}
				
				temp[histBlockCol]=bins;
			}
			
			ninePointHistogram[histBlockRow]=temp;
		}
		
		return ninePointHistogram;
	}
	
	public void createFeatureVector(double[][][] ninePointHistogram)
	{
		double[][][] featureVector=new double[15][7][36];
		
		for(int c=0; c<15; c++)
		{
			for(int r=0; r<7; r++)
			{
				for(int i=0; i<9; i++)
				{
					featureVector[c][r][i]=ninePointHistogram[c][r][i];
				}
				
				for(int j=9; j<18; j++)
				{
					featureVector[c][r][j]=ninePointHistogram[c+1][r][j-9];
				}
				
				for(int k=18; k<27; k++)
				{
					featureVector[c][r][k]=ninePointHistogram[c][r+1][k-18];
				}
				
				for(int l=27; l<36; l++)
				{
					featureVector[c][r][l]=ninePointHistogram[c][r+1][l-27];
				}
			}
		}
	}
	
	/*public void createFeatureVector(double[][][] ninePointHistogram)
	{
		double[][][] featureVector=new double[15][7][36];
		double epsilon=1e-05;
		
		for(int i=0; i<ninePointHistogram.length-1; i++)
		{
			double[][] temp=new double[7][36];
			
			for(int j=0; j<ninePointHistogram[i].length; j++)
			{
				double[][][] values=new double[2][2][9];
				
				for(int x=0; x<2; x++)
				{
					for(int y=0; y<2; y++)
					{
						values[y][x]=ninePointHistogram[y][x];
					}
				}
				
				double[] finalVector=new double[36];
				
				int z=0;
				for(double[][] k : values)
				{
					for(double[] l : k)
					{
						for(double m : l)
						{
							finalVector[z]=m;
							z++;
						}
					}
					//z=0;
					double sum=0;
					for(double x : finalVector)
					{
						sum+=Math.pow(x, 2);
					}
					
					sum=Math.sqrt(sum);
					
					for(double x : finalVector)
					{
						
					}
				}
			}
		}
	}*/
	
	public double[][][] normalize(double[][][] tmp)
	{
		double[][][] normalizedVector=new double[1][][];
		return normalizedVector;
	}
}