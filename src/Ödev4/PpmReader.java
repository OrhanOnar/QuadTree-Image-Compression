package Ödev4;
import java.util.*;
import java.io.*;
public class PpmReader{
	
	
	
    private int width;
    private int height;
    public int say=0;
    private Color[][] image;

    
    
    public PpmReader(String filename) throws FileNotFoundException{
    	
    	
	Scanner input = new Scanner(new File(filename));

	
	
	if(input==null)
		throw new  FileNotFoundException("null input");

	
	
	while(!input.hasNextInt()){
	    input.next();
	}

	
    width= input.nextInt();
	height= input.nextInt();
		
	input.nextInt();
	
	image = new Color[height][width];
	
	
	
	
	while(input.hasNext()){
	    
		
	    for(int i=0; i< this.height;i++){
		for(int j=0; j< this.width;j++){              //piksel okuma
		    int red = input.nextInt();
		    int green = input.nextInt();
		    int blue = input.nextInt();
		    image[i][j] = new Color(red,green,blue);
		}
	    }
	    
	}
	
	
    }

    
    
    
    public Color[][] getImage(){
    	
	return this.image;
	
    }

    
    
    public int getWidth(){
    	
	return this.width;
	
    }
    
    
    public int getHeight(){
    	
	return this.height;

    }
   
   
    
    

}



