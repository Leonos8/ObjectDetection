package Main;

/*
 * Image creation
 * Preprocess
 * FeatureExtraction
 * LinearSVM
 */

import javax.swing.SwingUtilities;

import org.opencv.core.Core;
import org.opencv.core.Mat;

import Classifier.FeatureExtraction;
import Classifier.ImageCreation;
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
				Mat[] oldMatrix=IC.createTrainingImageSet();
				
				Preprocess processor=new Preprocess(oldMatrix);
				
				Mat[] matrix=processor.getMatrix();
				
				FeatureExtraction feature=new FeatureExtraction(matrix);
				
				feature.runFeatureExtraction();
			}
		});
	}
}
