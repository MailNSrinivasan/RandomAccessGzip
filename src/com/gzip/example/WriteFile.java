package com.gzip.example;

import java.io.File;
import java.io.FileOutputStream;



public class WriteFile {

	
	public static void main(String[] args) {
		
		System.out.println("Strat");
		try {
            //FileInputStream fis = new FileInputStream(gzipFile);
           
			String filepath = "C:\\Users\\ctsuser\\Desktop\\gzip\\10_New_GBFile.txt";
			
            FileOutputStream fos = new FileOutputStream(filepath);
            StringBuilder sb = new StringBuilder();
            byte[] buffer = new byte[1024*1024];
            for (int i = 0; i < 1000*6; i++) {
            	sb.append("String test + "+i);
			}
            
            int i = 0;
            
            //long totLen = new File(filepath).length();
            long totLen = 0;
            
            String sbStr = sb.toString();
            
            byte[] buffer1 = sb.toString().getBytes();
            
            long gb_10 = 10737418240L;
            
           // Target
            
            while(totLen < (gb_10)) {
            	i++;
            	//System.out.println(i);
                fos.write(buffer1);
                //fos.write("\n".getBytes());
                totLen += buffer1.length;
                
                /*if(totLen > (1024*1024 *1024 *3) ){
                	System.out.println("3 gb ");
                }else if(totLen > 1024*1024 *1024){
                	System.out.println("1 gb ");
                }*/
                
                
            }
            
            //close resources
            fos.close();
            //gis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}
