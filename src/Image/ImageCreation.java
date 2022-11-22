package Image;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

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
	public static final String testImgPath=path+File.separator+"res"+File.separator;
	
	JFrame frame;

	JLabel picLabel;
	
	MatOfByte byteMat=new MatOfByte();
	
	int WIDTH=640;
	int HEIGHT=480;
	
	public ImageCreation()
	{
		//initializeFrame();
		
		Mat img=createImage();
		
    	//displayImage(img);
	}
	
	public Mat createImage()
	{
		Mat matrix=new Mat();
		
		matrix=Imgcodecs.imread(testImgPath+"Person1.png");
		Mat img=matrix.clone();
		
		Size sz = new Size(WIDTH, HEIGHT);
		Imgproc.resize(matrix, img, sz);
		
		return img;
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
