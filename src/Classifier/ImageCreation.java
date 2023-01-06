package Classifier;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class ImageCreation 
{
	public static final File currDir=new File(".");
	public static final String absolutePath=currDir.getAbsolutePath();
	public static final String path=absolutePath.substring(0, absolutePath.length()-2)+File.separator+"res"+File.separator+"Train";	
	public static final String positiveImgPath=path+File.separator+"Positive"+File.separator;
	public static final String negativeImgPath=path+File.separator+"Negative"+File.separator;
	
	JFrame frame;

	JLabel picLabel;
	
	MatOfByte byteMat=new MatOfByte();
	
	int WIDTH=64;
	int HEIGHT=128;
	
	public ImageCreation()
	{
		//initializeFrame();
		
		//Mat[] img=createTrainingImageSet();
		
    	//displayImage(img);
	}
	
	public Mat[] createTrainingImageSet()
	{
		int lines=numLines(positiveImgPath+"train.txt");
		
		
		ArrayList<Mat> mat=new ArrayList<>();
		//Mat[] matrix=new Mat[lines];
		try {
			Scanner sc=new Scanner(new File(positiveImgPath+"train.txt"));
			
			int count=0;
			int[][][] bounds = new int[lines][100][4];
			while(sc.hasNextLine())
			{
				String imageName=sc.nextLine();
				
				XMLParser xml=new XMLParser();
				
				bounds[count]=xml.parsePersonBounds(positiveImgPath+"Annotations"+File.separator+imageName+".xml");
				
				mat=crop(mat, Imgcodecs.imread(positiveImgPath+"JPEGImages"+File.separator+imageName+".jpg"), bounds[count]);
				
				log(count+1, lines, positiveImgPath+"Annotations"+File.separator+imageName+".jpg");
				
				count++;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Flips images but causes programs to crash
		/*System.out.println(mat.size());
		for(int i=0; i<mat.size(); i++)
		{
			Mat tmp=new Mat();
			System.out.println("Flip");
			Core.flip(mat.get(i), tmp, 0);
			mat.add(tmp);
		}
		System.out.println(mat.size());*/
		
		Mat[] matrix=convertALtoArray(mat);
		
		return matrix;
	}
	
	public Mat[] createNegativeImageSet()
	{
		int lines=numLines(negativeImgPath+"train.txt");
		Mat[] matrix=new Mat[lines];
		
		try {
			Scanner sc=new Scanner(new File(negativeImgPath+"train.txt"));
			
			int count=0;
			
			while(sc.hasNextLine())
			{
				String imageName=sc.nextLine();
				
				matrix[count]=Imgcodecs.imread(negativeImgPath+"0"+File.separator+imageName);
				
				log(count+1, lines, negativeImgPath+"Annotations"+File.separator+imageName);
				
				count++;
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return matrix;
	}
	
	public ArrayList<Mat> crop(ArrayList<Mat> matrix, Mat mat, int[][] bounds)
	{
		Mat tmp=new Mat();
		
		for(int i=0; i<bounds.length; i++)
		{
			tmp=mat.clone();
			Imgproc.resize(tmp, tmp, new Size(bounds[0][4], bounds[0][5]));
			
			Rect rectCrop=new Rect(bounds[i][0], bounds[i][1], bounds[i][2]-bounds[i][0], bounds[i][3]-bounds[i][1]);
			tmp=tmp.submat(rectCrop);
		
			matrix.add(tmp);
		}
		
		return matrix;
	}
	
	public Mat[] convertALtoArray(ArrayList<Mat> mat)
	{
		Mat[] matrix=new Mat[mat.size()];
		
		for(int i=0; i<mat.size(); i++)
		{
			matrix[i]=mat.get(i);
		}
		
		return matrix;
	}
	
	public int numLines(String path)
	{
		int lines=0;
		try {
			Scanner sc=new Scanner(new File(path));
			
			while(sc.hasNextLine())
			{
				sc.nextLine();
				lines++;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return lines;
	}
	
	public void initializeFrame()
	{
		frame = new JFrame("Image");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        picLabel = new JLabel();
        frame.setContentPane(picLabel);
        frame.setSize(WIDTH, HEIGHT);
        frame.setVisible(true);
	}
	
	public void displayImage(Mat img)
	{
		initializeFrame();
        BufferedImage bi=Mat2bufferedImage(img);
    	ImageIcon image = new ImageIcon(bi);
    	picLabel.setIcon(image);
    	picLabel.repaint();
	}
	
	public BufferedImage Mat2bufferedImage(Mat image) 
    {
        Imgcodecs.imencode(".jpg", image, byteMat);
        byte[] bytes = byteMat.toArray();
        InputStream in = new ByteArrayInputStream(bytes);
        BufferedImage img = null;
        
        try {
            img = ImageIO.read(in);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return img;
    }
	
	public void log(int count, int lines, String path)
	{
		System.out.println(count+"/"+lines+": "+path);
	}
}
