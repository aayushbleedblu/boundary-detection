import java.io.*;
import org.opencv.core.Core; 
import org.opencv.core.Mat;  
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.core.CvType;


public class writeup {
	public static void main(String args[]) throws IOException	{
		//Loading the OpenCV core library  
	      System.loadLibrary( Core.NATIVE_LIBRARY_NAME ); 
	     
	      //Instantiating the Imagecodecs class 
	      Imgcodecs imageCodecs = new Imgcodecs(); 
	     
	      //Reading the Image from the file  
	      String file ="C:\\Users\\Aayush Chudgar\\Desktop\\Proj\\reg_508_293_sub_1.tif"; 
	      Mat matrix = imageCodecs.imread(file); 
	      System.out.println("Image Loaded");
          
	      
	      int kernelSize = 5;
	      Mat afterconvo = new Mat();
	      Mat kernel = new Mat(kernelSize,kernelSize, CvType.CV_32F){
	            {
	            	put(0,0,-1);
	               put(0,1,-1);
	               put(0,2,-1);
	               put(0,3,-1);
	               put(0,4,-1);

	               put(1,0,-1);
	               put(1,1,-1);
	               put(1,2,-1);
	               put(1,3,-1);
	               put(1,4,-1);
	               
	               put(2,0,-1);
	               put(2,1,-1);
	               put(2,2,24);
	               put(2,3,-1);
	               put(2,4,-1);
	               
	               put(3,0,-1);
	               put(3,1,-1);
	               put(3,2,-1);
	               put(3,3,-1);
	               put(3,4,-1);
	               
	               put(4,0,-1);
	               put(4,1,-1);
	               put(4,2,-1);
	               put(4,3,-1);
	               put(4,4,-1);
	               
	            }
	         };	
	      
	      //convolution
	      Imgproc.filter2D(matrix, afterconvo, -1, kernel);

         //median
         Mat aftermed = new Mat();
         Imgproc.medianBlur(afterconvo, aftermed, 3);
               
	      //thresholding
         Mat afterthresh = new Mat();
         Imgproc.threshold(aftermed,afterthresh,195,255,Imgproc.THRESH_BINARY);
         
         //median
         Mat aftermed2 = new Mat();
         Imgproc.medianBlur(afterthresh, aftermed2, 3);

	      Mat joined = new Mat(998,998, CvType.CV_8U );
		  double[] sum={0};

	      for(int i=1; i<afterthresh.rows()-1; i++){
	    	  for(int j=1; j<afterthresh.cols()-1;j++){
	    		  sum[0]=0;
	    		  for(int p=i-1; p<i+2; p++){
	    			  for(int q=j-1; q<j+2; q++){
	    				  sum[0]=sum[0]+afterthresh.get(p, q)[0];
	    			  }
	    		  }
//	    		  sum[0]-=afterthresh.get(i, j)[0];
	    		  if(sum[0]>=510){
	    			  joined.put(i-1, j-1, 255);
	    		  }
	    		  else{
	    			  joined.put(i-1, j-1, 0);
	    		  }
	    	  }
	      }
	      
	      //median
	      Mat aftermed3 = new Mat();
	      Imgproc.medianBlur(joined, aftermed3, 3);
	      
	      //median
	      Mat aftermed4 = new Mat();
	      Imgproc.medianBlur(aftermed3, aftermed4, 3);
	      
	      Mat med4join = new Mat(998,998, CvType.CV_8U );
		  double[] sum1={0};

	      for(int i=1; i<aftermed4.rows()-1; i++){
	    	  for(int j=1; j<aftermed4.cols()-1;j++){
	    		  sum1[0]=0;
	    		  for(int p=i-1; p<i+2; p++){
	    			  for(int q=j-1; q<j+2; q++){
	    				  sum1[0]=sum1[0]+aftermed4.get(p, q)[0];
	    			  } 
	    		  }
//	    		  sum[0]-=afterthresh.get(i, j)[0];
	    		  if(sum1[0]>=510){
	    			  med4join.put(i-1, j-1, 255);
	    		  }
	    		  else{
	    			  med4join.put(i-1, j-1, 0);
	    		  }
	    	  }
	      }
	      
         

	         
		      // Creating an empty matrix to store the result
		      Mat threshsobel = new Mat();
		      // Applying sobel on the Image
		      Imgproc.Sobel(afterthresh, threshsobel, -1, 1, 1);
      
		      
	      //removing single pixel noise
	      Mat singlepixrem = new Mat(aftermed4.rows()-2,aftermed4.cols()-2, CvType.CV_8U );
		  double[] sum2={0};

	      for(int i=1; i<aftermed4.rows()-1; i++){
	    	  for(int j=1; j<aftermed4.cols()-1;j++){
	    		  sum2[0]=0;
	    		  for(int p=i-1; p<i+2; p++){
	    			  for(int q=j-1; q<j+2; q++){
	    				  sum2[0]=sum2[0]+aftermed4.get(p, q)[0];
	    			  }
	    		  }
	    		  sum2[0]-=aftermed4.get(i, j)[0];
	    		  if(sum2[0]==0){
	    			  singlepixrem.put(i-1, j-1, 0);
	    		  }
	    		  else{
	    			  singlepixrem.put(i-1, j-1, aftermed4.get(i, j)[0]);
	    		  }
	    	  }
	      }

	      
	      Mat sobeljoin = new Mat(threshsobel.rows()-2,threshsobel.cols()-2, CvType.CV_8U );
		  double[] sum3={0};

	      for(int i=1; i<threshsobel.rows()-1; i++){
	    	  for(int j=1; j<threshsobel.cols()-1;j++){
	    		  sum3[0]=0;
	    		  for(int p=i-1; p<i+2; p++){
	    			  for(int q=j-1; q<j+2; q++){
	    				  sum3[0]=sum3[0]+threshsobel.get(p, q)[0];
	    			  }
	    		  }
//	    		  sum[0]-=afterthresh.get(i, j)[0];
	    		  if(sum3[0]>=510){
	    			  sobeljoin.put(i-1, j-1, 255);
	    		  }
	    		  else{
	    			  sobeljoin.put(i-1, j-1, 0);
	    		  }
	    	  }
	      }
		  
	    //removing single pixel noise
	      Mat singlepixremsobel = new Mat(sobeljoin.rows()-2,sobeljoin.cols()-2, CvType.CV_8U );
		  double[] sum4={0};

	      for(int i=1; i<sobeljoin.rows()-1; i++){
	    	  for(int j=1; j<sobeljoin.cols()-1;j++){
	    		  sum4[0]=0;
	    		  for(int p=i-1; p<i+2; p++){
	    			  for(int q=j-1; q<j+2; q++){
	    				  sum4[0]=sum4[0]+sobeljoin.get(p, q)[0];
	    			  }
	    		  }
	    		  sum4[0]-=sobeljoin.get(i, j)[0];
	    		  if(sum4[0]==0){
	    			  singlepixremsobel.put(i-1, j-1, 0);
	    		  }
	    		  else{
	    			  singlepixremsobel.put(i-1, j-1, sobeljoin.get(i, j)[0]);
	    		  }
	    	  }
	      }

	      
	    //Writing the image 
	      imageCodecs.imwrite("sample5x5.tif", afterconvo);
	      imageCodecs.imwrite("samplethresh.tif", afterthresh);
	      imageCodecs.imwrite("samplemed.tif", aftermed);
	      imageCodecs.imwrite("joined.tif", joined);
	      imageCodecs.imwrite("threshsobel.tif", threshsobel);
	      imageCodecs.imwrite("threshsobel.tif", threshsobel);
	      imageCodecs.imwrite("singlepixremsobel.tif", singlepixremsobel);
	      imageCodecs.imwrite("singlepixrem.tif", singlepixrem);
	      imageCodecs.imwrite("singlepixrem.tif", singlepixrem);
	      
	      System.out.println("Images Written");
	      
	      File f = new File("coordinates.txt");
	      FileWriter fw = new FileWriter(f.getAbsoluteFile());
	      BufferedWriter bw = new BufferedWriter(fw);
	      for(int i=0; i<aftermed4.rows();i++){
	    	  for(int j=0; j<aftermed4.cols(); j++){
	    		  if(matrix.get(i, j)[0]>100)
	    		      bw.write(i+","+j+"\r\n");
	    	  }
	      }
	      bw.close(); // Be sure to close BufferedWriter
	}
	
}


