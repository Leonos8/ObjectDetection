package Driver;

import java.io.File;
import java.util.ArrayList;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import Classifier.ImageCreation;
import Classifier.LinearSVM;
import Classifier.XMLParser;

public class UnitTests {
	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		// cropTest();
		linearSVMTest();
	}

	public static void cropTest() {
		ImageCreation IC = new ImageCreation();
		ArrayList<Mat> matrix = new ArrayList<>();

		final File currDir = new File(".");
		final String absolutePath = currDir.getAbsolutePath();
		final String path = absolutePath.substring(0, absolutePath.length() - 2) + File.separator + "res"
				+ File.separator;
		final String trainImgPath = path + "Train" + File.separator + "Positive" + File.separator;

		XMLParser xml = new XMLParser();

		int[][] bounds = xml.parsePersonBounds(trainImgPath + "Annotations" + File.separator + "image (101)" + ".xml");

		matrix = IC.crop(matrix,
				Imgcodecs.imread(trainImgPath + "JPEGImages" + File.separator + "image (101)" + ".jpg"), bounds);

		for (int i = 0; i < matrix.size(); i++) {
			System.out.println(bounds[i][0] + " " + bounds[i][1] + " " + bounds[i][2] + " " + bounds[i][3]);
			IC.displayImage(matrix.get(i));
		}

		System.out.println(0);
	}

	public static void linearSVMTest()
	{
		double lr=.01; //Might make a method to calculate this?
		int c=30;
		
		double[][] p= {{2.6487,4.5192},
				{1.5438,2.4443},
				{1.8990,4.2409},
				{2.4711,5.8097},
				{3.3590,6.4423},
				{3.2406,5.8097},
				{3.8128,6.3917},
				{4.4441,6.8725},
				{3.6747,6.7966},
				{4.7401,8.1630},
				{3.8917,7.4038},
				{4.6020,7.6316},
				{5.7265,7.7581},
				{4.9571,6.5688},
				{3.9903,5.3543},
				{3.0236,4.4686},
				{2.0568,2.9757},
				{1.2676,2.4443},
				{1.1690,0.9008},
				{1.7411,2.1154},
				{1.3860,3.2794},
				{1.5636,4.1650},
				{1.8793,4.8482},
				{2.7868,3.3300},
				{3.5563,5.1518},
				{4.0693,6.2652},
				{4.3849,6.2652},
				{1.5438,7.2014},
				{2.4120,7.6569},
				{1.7806,6.1387},
				{1.4057,4.4939},
				{2.6093,4.8735},
				{3.0828,5.5314},
				{3.9311,6.0121},
				{4.7598,7.1508},
				{5.3122,7.7075},
				{5.7068,8.3148},
				{5.1149,8.5172},
				{5.4109,8.7449},
				{3.8128,7.8593},
				{3.2406,6.9990},
				{2.9052,5.5061},
				{2.6882,4.9241},
				{3.8325,6.6447},
				{4.5428,7.6822},
				{5.7857,8.0364},
				{6.5552,8.9221},
				{5.2530,7.8593},
				{5.2333,6.5941},
				{4.7598,6.0374}};
		
		double[][] n= {{4.5822,2.7227},
				{3.6549,1.9383},
				{2.9841,1.6852},
				{4.4244,4.3168},
				{3.7536,3.4312},
				{5.2728,5.4808},
				{4.8387,4.1144},
				{4.4244,3.2034},
				{5.3911,4.1144},
				{6.0817,5.1012},
				{5.5687,4.8988},
				{6.4565,5.9615},
				{6.0028,5.7591},
				{6.7722,6.6953},
				{6.6538,5.7338},
				{7.1471,6.6194},
				{7.5219,7.2014},
				{6.8314,7.2014},
				{7.6206,8.5931},
				{7.1865,7.7581},
				{7.7784,7.7581},
				{7.6009,5.1012},
				{6.4960,4.2156},
				{5.8055,3.4818},
				{5.0163,2.3684},
				{4.1876,1.7864},
				{3.4379,0.9008},
				{5.7857,0.9008},
				{6.3382,1.9636},
				{4.9571,1.4069},
				{6.8511,2.4190},
				{6.0817,2.8745},
				{7.1668,4.0132},
				{7.2260,4.6711},
				{8.1533,5.1771},
				{7.4825,6.2146},
				{7.0484,5.4555},
				{8.5084,5.9868},
				{7.5417,4.0891},
				{7.2063,2.3937},
				{6.5355,1.3310},
				{5.4503,1.7358},
				{5.8449,2.4443},
				{4.8979,3.1781},
				{5.8055,4.6711},
				{7.3641,5.9868},
				{6.2592,4.6711},
				{8.3703,7.5810},
				{8.5676,4.6457},
				{8.1676,4.6457}};
		
		LinearSVM lsvm=new LinearSVM(p, n, lr, c, 500); 
		//lsvm.distances();
	}
}
