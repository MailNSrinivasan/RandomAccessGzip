package com.java.util.zip.ra;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.zip.Deflater;

public class GzipRandomAccessFileUtil  {

	long originalFileSize;
	long compressedFileSize;
	
	
	public static void main11(String[] args) throws Exception, IOException {
		
		File gzipfile = new File("C:\\Users\\ctsuser\\Desktop\\gzip\\10_New_GBFile_Zip.gz");
		System.out.println("File Size  : "+gzipfile.length());
		
		GZipInputStreamRandomAccess gis = new GZipInputStreamRandomAccess(new FileInputStream(gzipfile));
         
         long startTime = System.currentTimeMillis();
         
         gis.skip(5368709120L);
         
         System.out.println(" Time taken to NORMAL Seek : "+(System.currentTimeMillis() - startTime));
         
         
         long counter = 0;
 		
		 byte[] buffer = new byte[1024];
         int len;
         
         while((len = gis.read(buffer)) != -1){
        	 counter += len;
        	 System.out.println(new String(buffer, 0, len));
        	 break;
         }
         
         System.out.println(" Rem length : "+counter);
	}

	
static public BestIndex getBestLastIndex(File gzipfile, long pos) throws IOException {
		
		long gzippedSize = gzipfile.length();
		
		long getUnCompressedSize = getUncompressedSize(gzipfile);
		
		
		//float compressedratio = (gzippedSize/getUnCompressedSize);
		
		
		long compressedSeekPos = (long)(((float)(((float)gzippedSize/(float)getUnCompressedSize)))*pos);
		
		
		System.out.println(" compressedSeekPos  : " + compressedSeekPos);
		
		
		long position =  compressedSeekPos;
		int readSize = 1 * 1024 * 1024;
		
		if (compressedSeekPos - readSize  < 0) {
			position = 0;
		}else {
			position = position - readSize;
		}
		
		RandomAccessFile raf = new RandomAccessFile(gzipfile, "r");
		raf.seek(position);
		
		byte[] bytes = new byte[readSize];
		raf.read(bytes);
		
		byte[] headerbytes = new byte[] {
                (byte) GZIP_MAGIC,        // Magic number (short)
                (byte)(GZIP_MAGIC >> 8),  // Magic number (short)
                Deflater.DEFLATED,        // Compression method (CM)
                (byte)(0 | (1 << 4)),     // Flags (FLG)
                0,                        // Modification time MTIME (int)
                0,                        // Modification time MTIME (int)
                0,                        // Modification time MTIME (int)
                0,                        // Modification time MTIME (int)
                0,                        // Extra flags (XFLG)
                0                         // Operating system (OS)
                
            };
		
		
		String bestIndex_OFFset = checkAndGetLastIndex(bytes, pos);
		long bestLastIndex = Long.parseLong(bestIndex_OFFset.split("-")[0])+position;
		
		long bestLastIndexCommentOffset = Long.parseLong(bestIndex_OFFset.split("-")[1]); 
		
		
		return new BestIndex(bestLastIndex, bestLastIndexCommentOffset);
		
	}

	static public GZipInputStreamRandomAccess seek(File gzipfile, long pos) throws IOException {
		
		long gzippedSize = gzipfile.length();
		
		long getUnCompressedSize = getUncompressedSize(gzipfile);
		
		
		//float compressedratio = (gzippedSize/getUnCompressedSize);
		
		
		long compressedSeekPos = (long)(((float)(((float)gzippedSize/(float)getUnCompressedSize)))*pos);
		
		
		System.out.println(" compressedSeekPos  : " + compressedSeekPos);
		
		
		long position =  compressedSeekPos;
		int readSize = 1 * 1024 * 1024;
		
		if (compressedSeekPos - readSize  < 0) {
			position = 0;
		}else {
			position = position - readSize;
		}
		
		RandomAccessFile raf = new RandomAccessFile(gzipfile, "r");
		raf.seek(position);
		
		byte[] bytes = new byte[readSize];
		raf.read(bytes);
		
		byte[] headerbytes = new byte[] {
                (byte) GZIP_MAGIC,        // Magic number (short)
                (byte)(GZIP_MAGIC >> 8),  // Magic number (short)
                Deflater.DEFLATED,        // Compression method (CM)
                (byte)(0 | (1 << 4)),     // Flags (FLG)
                0,                        // Modification time MTIME (int)
                0,                        // Modification time MTIME (int)
                0,                        // Modification time MTIME (int)
                0,                        // Modification time MTIME (int)
                0,                        // Extra flags (XFLG)
                0                         // Operating system (OS)
                
            };
		
		
		String bestIndex_OFFset = checkAndGetLastIndex(bytes, pos);
		long bestLastIndex = Long.parseLong(bestIndex_OFFset.split("-")[0])+position;
		
		long bestLastIndexCommentOffset = Long.parseLong(bestIndex_OFFset.split("-")[1]); 
		
		//InputStreamConvertor inpuStreamCon = new InputStreamConvertor(gzipfile, bestLastIndex);
		
		GZipInputStreamRandomAccess gis = new GZipInputStreamRandomAccess(gzipfile, bestLastIndex, pos - bestLastIndexCommentOffset);
		
		//gis.skip(pos - bestLastIndex);
		
		return gis;
		
	}


	private final static int GZIP_MAGIC = 0x8b1f;
	
	private static long getUncompressedSize(File gzipfile) throws IOException {

//		RandomAccessFile file = new RandomAccessFile(gzipfile, "r");
//		file.seek(position);
		int position = 0;
		int readSize = 1 * 1024 * 1024;
		
		if (gzipfile.length() < readSize) {
			position = 0;
			readSize = (int) gzipfile.length();
		} else {
			position = (int) (gzipfile.length() - readSize);
			//readSize = 1 * 1024 * 1024;
		}
		
		
		RandomAccessFile raf = new RandomAccessFile(gzipfile, "r");
		raf.seek(position);
		
		
		byte[] bytes = new byte[readSize];
		raf.read(bytes);
		
		
		byte[] headerbytes = new byte[] {
                (byte) GZIP_MAGIC,        // Magic number (short)
                (byte)(GZIP_MAGIC >> 8),  // Magic number (short)
                Deflater.DEFLATED,        // Compression method (CM)
                (byte)(0 | (1 << 4)),     // Flags (FLG)
                0,                        // Modification time MTIME (int)
                0,                        // Modification time MTIME (int)
                0,                        // Modification time MTIME (int)
                0,                        // Modification time MTIME (int)
                0,                        // Extra flags (XFLG)
                0                         // Operating system (OS)
                
            };
		
		
		
		
		int lastIndex = lastIndexOf(bytes, headerbytes, 0, bytes.length-1);
		
		//System.out.println("lastIndex :"+lastIndex);
		
		System.out.println("lastIndex :"+position+lastIndex);
		
		raf.seek((position+lastIndex));
		
		
		bytes = new byte[readSize];
		raf.read(bytes);
		
		byte[] comment = new byte[140];
		
		int j = 0;
		for (int i = 0; i < comment.length; i++) {
			
			
			System.out.print(bytes[i]);
			if(i < 10) {
				System.out.println(" - continue");
				continue;
			}
			if (bytes[i] == 0 ){
				break;
			}
			comment[j++] = bytes[i];
			
		}
		
		String offSetComment = new String(comment, 0, j);
		System.out.println("Comment : "+ offSetComment+"<<");
		
		
		
		
		
		//RandomAccessFile raf = new RandomAccessFile(gzipfile, "r");
		raf.seek(raf.length() - 4);
		int b4 = raf.read();
		int b3 = raf.read();
		int b2 = raf.read();
		int b1 = raf.read();
		int val = (b1 << 24) | (b2 << 16) + (b3 << 8) + b4;

		raf.close();
		
		
		long totalUncompressedSize = Long.parseLong(offSetComment.replaceAll("Offset:", ""))+val;
		
		return totalUncompressedSize;
	}
	
	/*private static int checkAndGetLastIndex(byte[] bytes, long position) {
		
		byte[] headerbytes = new byte[] {
                (byte) GZIP_MAGIC,        // Magic number (short)
                (byte)(GZIP_MAGIC >> 8),  // Magic number (short)
                Deflater.DEFLATED,        // Compression method (CM)
                (byte)(0 | (1 << 4)),     // Flags (FLG)
                0,                        // Modification time MTIME (int)
                0,                        // Modification time MTIME (int)
                0,                        // Modification time MTIME (int)
                0,                        // Modification time MTIME (int)
                0,                        // Extra flags (XFLG)
                0                         // Operating system (OS)
                
            };
		
		int lastIndex = bytes.length;
		 long offset = 0;
		int endCheck = bytes.length-1;
		 
		while(position > offset) {
			
			lastIndex = lastIndexOf(bytes, headerbytes, 0, endCheck);
			
			endCheck = lastIndex;
			raf.seek((position+lastIndex));
			
			
			bytes = new byte[readSize];
			raf.read(bytes);
			
			byte[] comment = new byte[140];
			
			int j = 0;
			for (int i = lastIndex; i < lastIndex+comment.length; i++) {
				
				
				System.out.print(bytes[i]);
				if(i < lastIndex+10) {
					System.out.println(" - continue");
					continue;
				}
				if (bytes[i] == 0 ){
					break;
				}
				comment[j++] = bytes[i];
				
			}
			
			String offSetComment = new String(comment, 0, j);
			System.out.println("Comment : "+ offSetComment+"<<");
			
			offset = Long.parseLong(offSetComment.replaceAll("Offset:", ""));
			
		}
		
		System.out.println( " ###############offset > "+offset);
		
		return lastIndex;
	}
	
	*/

	
	private static String  checkAndGetLastIndex(byte[] bytes, long position) {
		
		byte[] headerbytes = new byte[] {
                (byte) GZIP_MAGIC,        // Magic number (short)
                (byte)(GZIP_MAGIC >> 8),  // Magic number (short)
                Deflater.DEFLATED,        // Compression method (CM)
                (byte)(0 | (1 << 4)),     // Flags (FLG)
                0,                        // Modification time MTIME (int)
                0,                        // Modification time MTIME (int)
                0,                        // Modification time MTIME (int)
                0,                        // Modification time MTIME (int)
                0,                        // Extra flags (XFLG)
                0                         // Operating system (OS)
                
            };
		
		int lastIndex = bytes.length;
		 long offset = position+1;
		int endCheck = bytes.length-1;
		 
		while(position < offset) {
			
			lastIndex = lastIndexOf(bytes, headerbytes, 0, endCheck);
			
			endCheck = lastIndex;
			/*raf.seek((position+lastIndex));
			
			
			bytes = new byte[readSize];
			raf.read(bytes);*/
			
			byte[] comment = new byte[140];
			
			int j = 0;
			for (int i = lastIndex; i < lastIndex+comment.length; i++) {
				
				System.out.println(i);
				System.out.print(bytes[i]);
				if(i < lastIndex+10) {
					System.out.println(" - continue");
					continue;
				}
				if (bytes[i] == 0 ){
					break;
				}
				comment[j++] = bytes[i];
				
			}
			
			String offSetComment = new String(comment, 0, j);
			System.out.println("Comment : "+ offSetComment+"<<");
			
			offset = Long.parseLong(offSetComment.replaceAll("Offset:", ""));
			
		}
		
		System.out.println( " ###############offset > "+offset);
		
		return lastIndex+"-"+offset;
	}

	
	private static long getCommentOffset(byte[] bytes, int position) {
		
		byte[] comment = new byte[140];
		
		int j = 0;
		for (int i = position; i < position+comment.length; i++) {
			
			
			System.out.print(bytes[i]);
			if(i < position+10) {
				System.out.println(" - continue");
				continue;
			}
			if (bytes[i] == 0 ){
				break;
			}
			comment[j++] = bytes[i];
			
		}
		
		String offSetComment = new String(comment, 0, j);
		System.out.println("Comment : "+ offSetComment+"<<");
		
		long offset = Long.parseLong(offSetComment.replaceAll("Offset:", ""));
		return offset;
	}



	
	public static int indexOf(byte[] srcData, byte[] dataToFind, int iDataLen) {
        int iDataToFindLen = dataToFind.length;
        int iMatchDataCntr = 0;
        for (int i = 0; i < iDataLen; i++) {
            if (srcData[i] == dataToFind[iMatchDataCntr]) {
                iMatchDataCntr++;
            } else {
                if (srcData[i] == dataToFind[0]) {
                    iMatchDataCntr = 1;
                } else {
                    iMatchDataCntr = 0;
                }
            }
            if (iMatchDataCntr == iDataToFindLen) {
                return i-iDataToFindLen+1;
            } 
        }
        return -1;
    }
	
	public static int indexOf(byte[] srcData, byte[] dataToFind, int startIndex, int endIndex) {
        int iDataToFindLen = dataToFind.length;
        int iMatchDataCntr = 0;
        for (int i = startIndex; i < endIndex; i++) {
            if (srcData[i] == dataToFind[iMatchDataCntr]) {
                iMatchDataCntr++;
            } else {
                if (srcData[i] == dataToFind[0]) {
                    iMatchDataCntr = 1;
                } else {
                    iMatchDataCntr = 0;
                }
            }
            if (iMatchDataCntr == iDataToFindLen) {
                return i-iDataToFindLen+1;
            } 
        }
        return -1;
    }

	public static int lastIndexOf(byte[] srcData, byte[] dataToFind) {
        int iDataLen = srcData.length;
        int iDataToFindLen = dataToFind.length;
        int iMatchDataCntr = iDataToFindLen-1;
        for (int counter = iDataLen-1; counter >= 0; counter--) {
            if (srcData[counter] == dataToFind[iMatchDataCntr]) {
                iMatchDataCntr--;
            } else {
                iMatchDataCntr = iDataToFindLen-1;
            }
            if (iMatchDataCntr == 0) {
                return counter-1;
            } 
        }
        return -1;
    }
	
	public static int lastIndexOf(byte[] srcData, byte[] dataToFind, int startIndex, int endIndex) {
        int iDataLen = srcData.length;
        int iDataToFindLen = dataToFind.length;
        int iMatchDataCntr = iDataToFindLen-1;
        for (int counter = endIndex; counter >= startIndex; counter--) {
            if (srcData[counter] == dataToFind[iMatchDataCntr]) {
                iMatchDataCntr--;
            } else {
                iMatchDataCntr = iDataToFindLen-1;
            }
            if (iMatchDataCntr == 0) {
                return counter-1;
            } 
        }
        return -1;
    }
	
	
}

