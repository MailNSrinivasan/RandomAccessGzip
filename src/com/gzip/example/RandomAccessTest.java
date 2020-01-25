package com.gzip.example;

import java.io.File;
import java.io.FileInputStream;

import com.java.util.zip.ra.GZipInputStreamRandomAccess;
import com.java.util.zip.ra.GzipRandomAccessFileUtil;

public class RandomAccessTest {

	
	public static void main(String[] args) throws Exception {
		
		
		long startTime = System.currentTimeMillis();
		
		File gzipfile = new File("C:\\Users\\ctsuser\\Desktop\\gzip\\10_New_GBFile_Zip.gz");
		
		GZipInputStreamRandomAccess gis = GzipRandomAccessFileUtil.seek(gzipfile, 5368709120L);
		//GZipInputStreamRandomAccess gis = new GZipInputStreamRandomAccess(gzipfile, 5368709120L);
		
		//gis.seek(5368709120L);
		
		System.out.println(" Time taken to Seek : "+(System.currentTimeMillis() - startTime));

		long counter = 0;
		
		 byte[] buffer = new byte[1024];
         int len;
         while((len = gis.read(buffer)) != -1){
        	 counter += len;
        	 System.out.println(new String(buffer, 0, len));
        	 break;
         }
         
         System.out.println(" Rem length : "+counter);
         
         //close resources
         gis.close();
		
         //gis = GzipRandomAccessFileUtil.seek(gzipfile, 1024*1024*500);
		
         gis = new GZipInputStreamRandomAccess(new FileInputStream(gzipfile));
         
         startTime = System.currentTimeMillis();
         
         gis.skip(5368709120L);
         
         System.out.println(" Time taken to NORMAL Seek : "+(System.currentTimeMillis() - startTime));
         
         while((len = gis.read(buffer)) != -1){
        	 counter += len;
        	 System.out.println(new String(buffer, 0, len));
        	 break;
         }
         
         System.out.println(" Rem length : "+counter);
	}
	

}
