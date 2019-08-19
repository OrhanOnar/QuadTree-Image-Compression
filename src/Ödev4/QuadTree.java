package �dev4;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.*;
import java.util.*;

import javax.imageio.ImageIO;


public class QuadTree{
	
	
	public  int index = 1;
    private Node root;
    private double Seviye;
    private int h1;
    private int w1;
    private static Color[][] image;
    
    
	public static double red=0.0;
	public static double green=0.0;      
	public static double blue=0.0;
	
	double ortRED=0.0;
	double ortGREEN=0.0;
	double ortBLUE=0.0;              //meansquare hesabi icin
	double MeanSquaredError=0.0;

    
    private int threshold=0;          
    

    public Node getRoot(){
	return this.root;
    }

    public Color[][] getImage(){
	return this.image;
    }
    

    private class Node{

    	
    	
    	
	public int  xCor,yCor,w1,h1;
	public Color color;
	public Node[] child;
	

	public Node(int xCor, int yCor, int h, int w){
	    this.xCor=xCor;
	    this.yCor=yCor;
	    this.h1=h;
	    this.w1=w;
	    this.child= new Node[4];
	}
	


	
	public Color averageColor(){
		
		
	    double red=0.0;
	    double green=0.0;
	    double blue=0.0;
	    
	    for(int i=0; i<child.length;i++){
		if (child[i] != null){
			red+= child[i].getColor().getRed();                            
				green+= child[i].getColor().getGreen();
					blue+= child[i].getColor().getBlue();
		}
	    }
	    
	    red=red/child.length;
	    
	    	green=green/child.length;                             
	    	
	    		blue=blue/child.length;
	    
	    		return new Color((int)red,(int)green,(int)blue);
	    
	    
	}
	
	
	
	public Color getColor(){
	    return this.color;
	}
	public void setColor(Color newColor){
	    this.color = newColor;
	}
	public int getX(){
	    return this.xCor;
	}

	public int getY(){
	    return this.yCor;
	}

	public Node[] getChildNode(){
	    return this.child;
	}
	
	public int getWidth(){
	    return this.w1;
	}
	
	public int getHeight(){
	    return this.h1;
	}
	
	
	
	
	public boolean external(){
		
		if(this.child[0]==null && this.child[1]==null && this.child[2]==null && this.child[3]==null)
			return true;
		else
			return false;
	}
	
	
     
    }
   
	

	
    
    
   
    public QuadTree(String filename, String output, int index) throws Exception{

    	
    	
    	if(filename=="" && output==null && index>8) {
    		
    		throw new Exception("Empty file name");
    	}
    	
    	
    	
	PpmReader file = new PpmReader(filename);
	
	
	h1= file.getHeight();
	w1= file.getWidth();
	
	image = new Color[h1][w1];


	   changethreshold(index);             //compression level 
	
	   Color[][] temp=file.getImage();      

	    root = Compress(temp, 0,0,h1,w1);  //root olustur
	    
	    Traverse();                   //gez image doldur
	    
	    Seviye = Seviye/(h1*w1);
	    
	    
	    	WriteImage(output, image);
	    
	
}
    
    
    

    public void changethreshold(int index) {
		if(index-1==0)
			threshold=1000000;
		if(index-1==1)
			threshold=2000000;
		if(index-1==2)
			threshold=50000;
		if(index-1==3)
			threshold=15000;
		if(index-1==4)
			threshold=35000;
		if(index-1==5)
			threshold=10000;
		if(index-1==6)
			threshold=5000;
		if(index-1==7)
			threshold=3700;
		
	}

	

	public void WriteImage(String filename, Color[][] img) throws IOException{
    	PrintWriter out = new PrintWriter(filename);
    		out.print("P3 ");
    	out.println(img[0].length+" "+img.length+" 255");
    	for(int i=0; i<img.length; i++){                                          //ppm olarak yaz
    	   for(int j=0;j<img[0].length;j++){
    		out.print(img[i][j].getRed()+" "+img[i][j].getGreen()+" "+img[i][j].getBlue()+" ");
    	    }
    	         out.println();
    			}
    			out.close();
       			}
    
	
	
private Node Compress(Color[][] imagePix, int i, int j, int h, int w){
    	
	
		Node node= new Node(j, i, h, w);         
		
		
		
		
		
		Color temp =  this.getNodeColor(imagePix,i,j,h,w);
		
		if(h==1 && w==1){                                //ikisi de 1 se yaz
		  node.setColor(imagePix[i][j]);
		    
		}
		else if (h==1 || w==1){ 
		    if(h==1){                                         //h1 se w 4 ten kucukse  bosluk kadar arttır 
			if(w <= 4 ){
			  
			    for(int say=0; say<w; say++){ 
			    	
				node.getChildNode()[say] = Compress(imagePix, i, j+say,h,1);
				
			    }
		    
			}

			else{
				
			    int W = w/2;                                  //kucuk degilse yariya indir ekle
			    
			    node.getChildNode()[0] = Compress(imagePix,i,j,1,W);
			    
			    	node.getChildNode()[1] = Compress(imagePix,i,j+W,1,w-W);
			    
			}
			
			
		    }
		    
		    
		    
		    else{
			if( h<=4 ){
			    for(int k=0; k<h; ++k){
				node.getChildNode()[k] = Compress(imagePix,i+k,j,1,w);            //usttekinin aynisi
			    }
			}
			else{
			    int H=h/2;
			    node.getChildNode()[0] = Compress(imagePix,i,j,H,1);
			    node.getChildNode()[1] = Compress(imagePix,i+H,j,h-H,1);
			}
		    }
		    node.setColor(node.averageColor());
		}
		else if (temp!= null){
		    node.setColor(temp);
		   }
		else{
			
		    int Height= h/2;                                                     
		    
		    	int Width= w/2;
		    
		  
		    node.getChildNode()[0] = Compress(imagePix,i,j+Width,Height,w-Width);                                //bir onceki metodda ona uygun null donerdi
		    	node.getChildNode()[1] = Compress(imagePix,i,j,Height,Width);
		    		node.getChildNode()[2] = Compress(imagePix,i+Height,j,h-Height,Width);               // If a subimage has error greater than some threshold, split into four further subimages.
		    			node.getChildNode()[3] = Compress(imagePix,i+Height,j+Width,h-Height,w-Width);
		    
		    node.setColor(node.averageColor());
		    
		}
		return node;
	    }
	  
	 


	    private Color getNodeColor(Color[][] pixels, int i, int j, int h,int w){
	    	
	    	
		ArrayList<Color> renk =new ArrayList<>();
	
		red=0;
		green=0;
		blue=0;
		
		int count=0;
		
		for(int say1=0; say1<h; say1++){
		    for(int say2=0; say2<w;say2++){
		    	
			count++;
				Color c= pixels[say1+i][say2+j];
					renk.add(c);
						
			red+=c.getRed();			                       //renkleri topla
			green+=c.getGreen();
			blue+=c.getBlue();
			
			
		    }
		}
	
				ortRED = red/count;
				ortGREEN = green/count;              //ortalama al
				ortBLUE = blue/count;
				 
				 MeanSquaredError =0.0;            //tek tek doner

		for(int sayac=0 ; sayac< renk.size() ;sayac++){
			
		    int cRed = renk.get(sayac).getRed();
		    int cGreen = renk.get(sayac).getGreen();                          
		    int cBlue = renk.get(sayac).getBlue();                                  

		    MeanSquaredError += Math.pow(cRed-ortRED,2)+ Math.pow(cGreen-ortGREEN,2)+ Math.pow(cBlue-ortBLUE,2);                    //s ((r − Ci .r) 2 + (g − Ci .g) 2 + (b − Ci .b) 2
		    
		}
		
		if(MeanSquaredError<this.threshold) {
			
			return new Color((int)ortRED,(int)ortGREEN,(int)ortBLUE);
			
		}
		else {
			
			return null;
		
		}
		
		
	    }
    
	    
	    
	 
	   

	    private void traverseHelper(Node node){
	    	
	    	if (!node.external()){
		    for (int i=0; i<4;i++){                        //internal  node sa alma devam et
			if (node.getChildNode()[i] != null) {
			traverseHelper (node.getChildNode()[i]);                       
			}
		    }
		}
	    	if (node.external()){
			
		    this.Seviye++;
		    for(int i=node.getY(); i< (node.getY()+node.getHeight()); i++){         
			for(int j=node.getX(); j<(node.getX()+node.getWidth()); j++){
			   if(node.getColor()!=null)
			    image[i][j] = node.getColor();                       //externalsa seviye arttir x ve ye  icin renk piksellerini doldur
			   }
		    }
		}
	    }
	    

	    public void Traverse(){
	    	traverseHelper(this.root);
	    }
	    
	    
	    
	    
}


	    
		
	  
	    
	