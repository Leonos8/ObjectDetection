package Main;

import javax.swing.SwingUtilities;

import org.opencv.core.Core;

import Classifier.FeatureExtraction;

public class Main 
{
	public static void main(String[] args)
	{
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				//ImageCreation img=new ImageCreation();
				
				//Preprocess processor=new Preprocess();
				
				FeatureExtraction feature=new FeatureExtraction();
			}
		});
	}
}
