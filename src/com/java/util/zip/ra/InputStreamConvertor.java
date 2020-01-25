/**
 * 
 */
package com.java.util.zip.ra;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;




public class InputStreamConvertor extends InputStream {
	
	RandomAccessFile raf;
	
	public InputStreamConvertor() {
		throw new UnsupportedOperationException();
	}
	
	public InputStreamConvertor(File gzipfile, long bestLastIndex, String temp) throws IOException {
		this.raf = new RandomAccessFile(gzipfile, "r");
		raf.seek(bestLastIndex);
	}

	public InputStreamConvertor(File gzipfile, long skipBytes) throws IOException {
		this.raf = new RandomAccessFile(gzipfile, "r");
		
		BestIndex bestIndex = GzipRandomAccessFileUtil.getBestLastIndex(gzipfile, skipBytes);
		
		raf.seek(bestIndex.getBestLastIndex());
		
	}

	public InputStreamConvertor(File gzipfile) throws IOException {
		this.raf = new RandomAccessFile(gzipfile, "r");
		
		
	}
	public  RandomAccessFile getRandomAccessFile() {
		return this.raf;

	}
	
	@Override
	public int read() throws IOException {
		return raf.read();
    	
    }

	@Override
    public int read(byte[] buf, int off, int len) throws IOException {
    	return raf.read(buf, off, len);
    }
    
    @Override
    public int read(byte[] b) throws IOException {
    	return raf.read(b);
    }
    

	@Override
	public void close() throws IOException {
		raf.close();
		super.close();
	}
	
	@Override
	public int available() throws IOException {
		return (int)(raf.length() - raf.getFilePointer());
	}
	
	
	public static void main(String[] args) throws FileNotFoundException {
		
	}
	
	
}

