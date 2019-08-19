package Ödev4;

import java.util.*;
import java.io.*;

public class Driver{
	
	public static String input="";
	public static String output="";
	public static     int index=1;	
	public static QuadTree agac;
	public static QuadTree agac2;
	public static String kontrol="";
	
    public static void main(String[] args) throws Exception{
    	
    	
    	
    	for(int i=0; i<args.length-1;i++){               //arguman kontrolu
    	    if(args[i].equals( "-i")) 
    	    	input = args[i+1];
    	    
    	    if(args[i].equals("-o"))                    
    	    	output = args[i+1];
    	}
    	
    	for (int i = 0; i < args.length; i++) {
		
    		kontrol+=args[i]+",";

		}
    	
    	
if(!(kontrol.contains("-i")  && kontrol.contains("-o") && kontrol.contains("-c")))
	throw new Exception("Eksik argüman");

	
	    for(int i=0; i<8;i++){


		QuadTree image = new QuadTree(input,output+"-"+(i+1)+".ppm",index);                
    	System.out.println("Compressed Resim- "+index +"  olusturuluyor.");

		index++;
	    }
	
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	
    }
}


	
