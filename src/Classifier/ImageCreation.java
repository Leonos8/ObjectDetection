package Classifier;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class ImageCreation 
{
	public static final File currDir=new File(".");
	public static final String absolutePath=currDir.getAbsolutePath();
	public static final String path=absolutePath.substring(0, absolutePath.length()-2);	
	public static final String trainImgPath=path+File.separator+"res"+File.separator+"Train"+File.separator;
	
	JFrame frame;

	JLabel picLabel;
	
	MatOfByte byteMat=new MatOfByte();
	
	int WIDTH=640;
	int HEIGHT=480;
	
	public ImageCreation()
	{
		//initializeFrame();
		
		//Mat[] img=createTrainingImageSet();
		
    	//displayImage(img);
	}
	
	public Mat[] createTrainingImageSet()
	{
		int lines=numLines(trainImgPath+"train.txt");
		
		Mat[] matrix=new Mat[lines];
		int i=0;
		
		try {
			Scanner sc=new Scanner(new File(trainImgPath+"train.txt"));
			
			while(sc.hasNextLine())
			{
				String imageName=sc.nextLine();
				
				matrix[i]=Imgcodecs.imread(trainImgPath+"JPEGImages"+File.separator+imageName+".jpg");
				Size sz=new Size(WIDTH, HEIGHT);
				Imgproc.resize(matrix[i], matrix[i], sz);
				i++;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
}
