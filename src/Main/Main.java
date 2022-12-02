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
import Classifier.LinearSVM;
import Classifier.Preprocess;
import Classifier.XMLParser;

public class Main 
{
	public static void main(String[] args)
	{
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		XMLParser xml=new XMLParser();
		
		int[][] test=xml.parsePersonBounds
				("C:\\Users\\covic\\OneDrive\\Documents\\GitHub\\ObjectDetection\\res\\Train\\Annotations\\image (244).xml");
		
		for(int i=0; i<test.length; i++)
		{
			for(int j=0; j<test[i].length; j++)
			{
				System.out.println(test[i][j]);
			}
			System.out.println();
		}
		
		
		
		
		
		
		
		
		
		
		
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
				
				double[][] featureVector=feature.getFeatureVector();
				
				//Will edit these accordingly
				int epochs=500;
				int batch=100;
				double learningRate=.5;
				double C=1; //Might make a method to calculate this?
				
				LinearSVM lsvm=new LinearSVM(featureVector, epochs, batch, learningRate, C);
			}
		});
	}
}
