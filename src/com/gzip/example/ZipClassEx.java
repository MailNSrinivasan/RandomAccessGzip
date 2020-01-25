package com.gzip.example;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipClassEx {

	public static void main(String[] args) throws Exception {
		
		//FileInputStream fis = new FileInputStream("C:\\Users\\ctsuser\\Desktop\\gzip\\Task.txt.gz");
		FileInputStream fis = new FileInputStream("C:\\Users\\ctsuser\\Desktop\\gzip\\gzip.zip");
		
		
		ZipInputStream zin = new ZipInputStream(new BufferedInputStream(fis));
		
		ZipEntry entry;
		while((entry = zin.getNextEntry()) != null) {
			System.out.println(entry.getComment());
		   // extract data
		   // open output streams
		}
		
	}
}
