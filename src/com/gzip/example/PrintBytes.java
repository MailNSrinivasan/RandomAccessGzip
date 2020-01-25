package com.gzip.example;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.java.util.zip.ra.GZIPOutputStreamRandomAccess;
 
public class PrintBytes {
 
    public static void main(String[] args) {
        //String file = "C:\\Users\\ctsuser\\Desktop\\Task.txt";
        String gzipFile = "C:\\Users\\ctsuser\\Desktop\\gzip\\Task.txt.gz";
        //String gzipFile = "C:\\Users\\ctsuser\\Desktop\\gzip\\Task.txt111.gz";
        
       // String newFile = "/Users/pankaj/new_sitemap.xml";
         
        printBytes( gzipFile);
         
      //  decompressGzipFile(gzipFile, newFile);
                
    }
 
   
 
    private static void printBytes(String gzipFile) {
        try {
            FileInputStream fis = new FileInputStream(gzipFile);
            
           // FileOutputStream fos = new FileOutputStream(gzipFile);
          //  GZIPOutputStream gzipOS = new GZIPOutputStream(fos);
            byte[] buffer = new byte[1024];
            int len;
            while((len=fis.read(buffer)) != -1){
                //gzipOS.write(buffer, 0, len);
                
                for (int i = 0; i < len; i++) {
                	System.out.println(buffer[i]);
					
				}
                
            }
            //close resources
           // gzipOS.close();
          //  fos.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
         
    }
 
}