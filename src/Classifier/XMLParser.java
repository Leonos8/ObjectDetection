package Classifier;

import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class XMLParser 
{
	
	DOMParser dom=new DOMParser();
	
	
	
	
	public int[][] parsePersonBounds(String path)
	{
		int[][] bounds=new int[numLines(path)][4];
		try {
			Scanner sc=new Scanner(new File(path));
			int i=0;
			while(sc.hasNext())
			{
				String line=sc.nextLine();
				if(line.contains(">person<"))
				{
					while(!line.equals("bndbox"))
					{
						sc.nextLine();
					}
					sc.nextLine();
					String val=sc.nextLine();
					val=val.substring(val.indexOf('>'), val.indexOf('<'));
					int xmin=Integer.parseInt(val);
					
					val=sc.nextLine();
					val=val.substring(val.indexOf('>'), val.indexOf('<'));
					int ymin=Integer.parseInt(val);
					
					val=sc.nextLine();
					val=val.substring(val.indexOf('>'), val.indexOf('<'));
					int xmax=Integer.parseInt(val);
					
					val=sc.nextLine();
					val=val.substring(val.indexOf('>'), val.indexOf('<'));
					int ymax=Integer.parseInt(val);
					
					bounds[i][0]=xmin;
					bounds[i][1]=ymin;
					bounds[i][2]=xmax;
					bounds[i][3]=ymax;
					i++;
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return bounds;
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
}
