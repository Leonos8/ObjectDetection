package Driver;

/*
 * Image creation
 * Preprocess
 * FeatureExtraction
 * LinearSVM
 */

import javax.swing.SwingUtilities;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import Classifier.FeatureExtraction;
import Classifier.ImageCreation;
import Classifier.LinearSVM;
import Classifier.Preprocess;

public class Main 
{
	public static void main(String[] args)
	{
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				ImageCreation IC=new ImageCreation();
				Mat[] oldPositiveMatrix=IC.createTrainingImageSet();
				Mat[] oldNegativeMatrix=IC.createNegativeImageSet();
				
				log(0);
				
				Preprocess processor=new Preprocess();
				
				processor.setMatrixArray(oldPositiveMatrix);
				processor.applyGammaCorrection();
				Mat[] positiveMatrix=processor.getMatrix();
				
				processor.setMatrixArray(oldNegativeMatrix);
				processor.applyGammaCorrection();
				Mat[] negativeMatrix=processor.getMatrix();
				
				log(1);
				
				FeatureExtraction feature=new FeatureExtraction();
				
				feature.setMatrix(positiveMatrix);
				feature.runFeatureExtraction();
				double[][] positiveFeatureVector=feature.getFeatureVector();
				
				feature.setMatrix(negativeMatrix);
				feature.runFeatureExtraction();
				double[][]negativeFeatureVector=feature.getFeatureVector();
				
				log(2);
				
				//Will edit these accordingly
				double lr=.01; //Might make a method to calculate this?
				int c=30;
				
				LinearSVM lsvm=new LinearSVM(positiveFeatureVector, negativeFeatureVector, lr, c, 500); 
				
				//log(3);
			}
		});
	}
	
	public static void log(int val)
	{
		if(val==0)
		{
			System.out.println("\nImage collection complete...\n");
		}
		else if(val==1)
		{
			System.out.println("\nPre-processing complete...\n");
		}
		else if(val==2)
		{
			System.out.println("\nFeature extraction complete...\n");
		}
		else if(val==3)
		{
			System.out.println("\nLinear SVM training complete...\n");
		}
	}
}
