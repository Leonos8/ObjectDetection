package Classifier;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLParser 
{
	public int[][] parsePersonBounds(String path)
	{
		int[][] bounds = null;
		try {
			File inputFile = new File(path);
	        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	        Document doc = dBuilder.parse(inputFile);
	        doc.getDocumentElement().normalize();
	         
	        NodeList nList = doc.getElementsByTagName("object");
	        NodeList boundList=doc.getElementsByTagName("size");
	        
	        Node node=boundList.item(0);
	        
	        int width=0; //col
	        int height=0; //height
	        
	        if (node.getNodeType() == Node.ELEMENT_NODE) 
        	{
	        	Element element = (Element) node;
	        	width=Integer.parseInt(element.getElementsByTagName("width").item(0).getTextContent());
	        	height=Integer.parseInt(element.getElementsByTagName("height").item(0).getTextContent());

        	}
	        
	        bounds=new int[nList.getLength()][6];
	        for(int i=0; i<nList.getLength(); i++)
	        {
	        	if(i<100)
	        	{
	        		Node nNode=nList.item(i);
		        	if (nNode.getNodeType() == Node.ELEMENT_NODE) 
		        	{
		        		Element eElement = (Element) nNode;
			            bounds[i][0]=Integer.parseInt(eElement.getElementsByTagName("xmin").item(0).getTextContent());
			            bounds[i][1]=Integer.parseInt(eElement.getElementsByTagName("ymin").item(0).getTextContent());
			            bounds[i][2]=Integer.parseInt(eElement.getElementsByTagName("xmax").item(0).getTextContent());
			            bounds[i][3]=Integer.parseInt(eElement.getElementsByTagName("ymax").item(0).getTextContent());
			            bounds[i][4]=width;
			            bounds[i][5]=height;
		        	}
	        	}
	        }
	        
	        
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return bounds;
	}
}
