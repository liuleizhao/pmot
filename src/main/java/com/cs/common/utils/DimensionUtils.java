package com.cs.common.utils;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;   
import javax.imageio.ImageIO;  
import javax.servlet.http.HttpServletRequest;

import java.io.File;  
import java.io.OutputStream;  
import java.io.IOException;  
import java.util.Hashtable;
import java.awt.image.BufferedImage;  

public final class DimensionUtils {
	
	
	private static final int BLACK = 0xFF000000;  
    private static final int WHITE = 0xFFFFFFFF;  
    
    private static final String folderName = "dimension";
    
	
	private DimensionUtils(){}
	
	
	public static BufferedImage toBufferedImage(BitMatrix matrix) {  
	      int width = matrix.getWidth();  
	      int height = matrix.getHeight();  
	      BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);  
	      for (int x = 0; x < width; x++) {  
	        for (int y = 0; y < height; y++) {  
	          image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);  
	        }  
	      }  
	      return image;  
	    }
	    
	    public static void writeToFile(BitMatrix matrix, String format, File file)  
	            throws IOException {  
	          BufferedImage image = toBufferedImage(matrix); 
	          
	          if (!ImageIO.write(image, format, file)) {  
	            throw new IOException("Could not write an image of format " + format + " to " + file);  
	          }  
	        }  
	        
	          
	    public static void writeToStream(BitMatrix matrix, String format, OutputStream stream)  
	        throws IOException {  
	      BufferedImage image = toBufferedImage(matrix);  
	      if (!ImageIO.write(image, format, stream)) {  
	        throw new IOException("Could not write an image of format " + format);  
	      }  
	    } 
	    
	    
	    public static void create2code(HttpServletRequest req,String data) throws WriterException, IOException
	    {
	    	
	    	int width = 130;  
	        int height = 130;  

	        String format = "gif";  
	        Hashtable <EncodeHintType, Object>hints = new Hashtable<EncodeHintType, Object>();  
	
	        hints.put(EncodeHintType.CHARACTER_SET, "utf-8"); 
	        hints.put(EncodeHintType.MARGIN, 1);
	        
	        BitMatrix bitMatrix = new MultiFormatWriter().encode(data,  
	                BarcodeFormat.QR_CODE, width, height, hints);  
	        
	        
	        
	        
	        String appPath = req.getSession().getServletContext().getRealPath("/")+"static/";
	        String fileName = appPath+folderName+"/"+data+".gif";
	        File folder = new File(appPath+folderName+"/");
	        
	        if(!folder.exists())
	        {
	        	folder.mkdirs();
	        }
	        
	        File outputFile = new File(fileName);  
	        DimensionUtils.writeToFile(bitMatrix, format, outputFile);  
	       
	      /*  File outputFile = new File("d:"+File.separator+data+"2code.gif");  
	        DimensionUtils.writeToFile(bitMatrix, format, outputFile);  */
	    }
	    
	    
	    public static void create1code(HttpServletRequest req,String data) throws WriterException, IOException
	    {
	    	
	    	
	    	int width = 200;  
	        int height = 40;  
	        
	        String format = "gif";  
	        Hashtable <EncodeHintType, Object>hints = new Hashtable<EncodeHintType, Object>();  
	       
	        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");  
	        hints.put(EncodeHintType.MARGIN, 1);
	        BitMatrix bitMatrix = new MultiFormatWriter().encode(data,  
	                BarcodeFormat.CODE_128, width, height, hints);  
	        
	        
	       
	        String appPath = req.getSession().getServletContext().getRealPath("/")+"static/";
	        String fileName = appPath+folderName+"/"+data+".gif";
	        File folder = new File(appPath+folderName+"/");
	        
	        if(!folder.exists())
	        {
	        	folder.mkdirs();
	        }
	        
	        File outputFile = new File(fileName);  
	        DimensionUtils.writeToFile(bitMatrix, format, outputFile);  
	    	
	    }
	    
	    

}
