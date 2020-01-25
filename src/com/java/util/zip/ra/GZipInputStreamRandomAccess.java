package com.java.util.zip.ra;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.Inflater;



public class GZipInputStreamRandomAccess extends GZIPInputStream{

	
	static final int  ONE_MB = 1024 *1024;
	
	
	public GZipInputStreamRandomAccess(InputStream in) throws IOException {
		super(in);
	}
	
	public GZipInputStreamRandomAccess(InputStream in, int size) throws IOException {
		super(in, size);
	}
	
	/*public GZipInputStreamRandomAccess(File gzipfile, long skipBytes) throws FileNotFoundException, IOException {
		//InputStreamConvertor inpuStreamCon = new InputStreamConvertor(gzipfile, bestLastIndex);
		
		super(new InputStreamConvertor(gzipfile));
		
		 BestIndex bestIndex = GzipRandomAccessFileUtil.getBestLastIndex(gzipfile, skipBytes);
		
		 System.out.println(" ((InputStreamConvertor)in).getRandomAccessFile(). >>>> "+((InputStreamConvertor)in).getRandomAccessFile().getFilePointer());
		((InputStreamConvertor)in).getRandomAccessFile().seek(0);
		((InputStreamConvertor)in).getRandomAccessFile().seek(bestIndex.getBestLastIndex());
		
		skip(skipBytes - bestIndex.getBestLastIndexCommentOffset());
		
	}*/


	public GZipInputStreamRandomAccess(File gzipfile, long bestLastIndex, long skipBytes) throws FileNotFoundException, IOException {
		//InputStreamConvertor inpuStreamCon = new InputStreamConvertor(gzipfile, bestLastIndex);
		super(new InputStreamConvertor(gzipfile, bestLastIndex, ""));
		
		System.out.println( "skiping... "+skipBytes);
		
		skip(skipBytes);
		
	}
	
	
	

	public Inflater getInflater() {
		return inf;
	}
	
	public long skip(long paramLong) throws IOException {
		
		
		//System.out.println(" Skiping :" + paramLong);
		
		if (paramLong < 0L) {
			throw new IllegalArgumentException("negative skip length");
		}
		long target = 1024*1024*1024;
		
		long skippedBytes = 0;
		int byteArraySize = ONE_MB / 2;
		byte[] buffer = new byte[byteArraySize];
		
		long readableBytes = 0;
		int readableBytesInt = 0;
				
		while (paramLong > skippedBytes) {
			readableBytes =  (paramLong - skippedBytes) > byteArraySize ? byteArraySize :  (paramLong - skippedBytes);
			if(readableBytes > (1024 *1024 * 1024)) {
				readableBytesInt = 1024 * 1024 *1024;
			}else {
				readableBytesInt =  (int) readableBytes;
			}
			int count = read(buffer, 0, readableBytesInt);
			if (count < 0) {
				break;
			}
			if(count == 0){
				System.out.println(" --");
			}
			skippedBytes += count;
			
			/*if(skippedBytes > target){
				System.out.println("Skipped .. "+ skippedBytes);
				target = target + (1024*1024*1024);
			}*/
		}
		buffer = null;
		return skippedBytes;
	}
	
	
	
	@Override
	public int read(byte[] buf, int off, int len) throws IOException {
		
		
		return super.read(buf, off, len);
	}
	
	@Override
	public int read() throws IOException {
		// TODO Auto-generated method stub
		return super.read();
	}
	
	@Override
	public int read(byte[] b) throws IOException {
		// TODO Auto-generated method stub
		return super.read(b);
	}

	
	

}