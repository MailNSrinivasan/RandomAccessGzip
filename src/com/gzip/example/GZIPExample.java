package com.gzip.example;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

import com.java.util.zip.ra.GZIPOutputStreamRandomAccess;
 
public class GZIPExample {
 
    public static void main(String[] args) {
        String file = "C:\\Users\\ctsuser\\Desktop\\gzip\\10_New_GBFile.txt";
        String gzipFile = "C:\\Users\\ctsuser\\Desktop\\gzip\\10_New_2_GBFile_Zip.gz";
        
       // String newFile = "/Users/pankaj/new_sitemap.xml";
         
        compressGzipFile(file, gzipFile);
         
      //  decompressGzipFile(gzipFile, newFile);
                
    }
 
    private static void decompressGzipFile(String gzipFile, String newFile) {
        try {
            FileInputStream fis = new FileInputStream(gzipFile);
            GZIPInputStream gis = new GZIPInputStream(fis);
            FileOutputStream fos = new FileOutputStream(newFile);
            byte[] buffer = new byte[1024];
            int len;
            while((len = gis.read(buffer)) != -1){
                fos.write(buffer, 0, len);
            }
            //close resources
            fos.close();
            gis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
         
    }
 
    private static void compressGzipFile(String file, String gzipFile) {
        try {
        	
        	long offset = 0 ;
        	
        	
            FileInputStream fis = new FileInputStream(file);
            FileOutputStream fos = new FileOutputStream(gzipFile);
            
            
            GZIPOutputStreamRandomAccess gzipOS = new GZIPOutputStreamRandomAccess(fos);
            byte[] buffer = new byte[1024];
            int len;
            
            //String tempStr = "Srini";
            
            while((len=fis.read(buffer)) != -1){
            	 gzipOS.write(buffer, 0, len);
            	 offset += len;
            }
            //close resources
            gzipOS.close();
            fos.close();
            fis.close();
            
            
           /* 
            * 
            
            fis = new FileInputStream(file);
            fos = new FileOutputStream(gzipFile, true);
            
            gzipOS = new GZIPOutputStreamRandomAccess(fos, offset);
            buffer = new byte[1024];
           
            while((len=fis.read(buffer)) != -1){
                gzipOS.write(buffer, 0, len);
            }
            //close resources
            gzipOS.close();
            fos.close();
            fis.close();*/
            
        } catch (IOException e) {
            e.printStackTrace();
        }
         
    }
 
}