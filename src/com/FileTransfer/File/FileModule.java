package com.FileTransfer.File;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
public class FileModule {

	/*
	 * 大文件分包
	 * @param file 文件
	 * @param blockLen 包大小
	 * @param seek 位置
	 * 
	 */
	public byte[] FileSplit(File file, int blockLen, long seek){
		byte[] blockCont = null; // 子块包的字节数组
		if(file.exists()){
			RandomAccessFile raf = null;
			ByteArrayOutputStream  baos = null;
			try {
				raf = new RandomAccessFile(file, "r");
				baos = new ByteArrayOutputStream();
				byte[] tempbytes = null;
				/*
				 * 剩余文件长度小于或等于块长度，自动为一块，seek移动到文件末尾位置
				 */
				if((file.length() - seek) > 0 && (file.length() - seek) <= blockLen){
					tempbytes = new byte[(int) (file.length() - seek)];
					raf.read(tempbytes);
					baos.write(tempbytes, 0, tempbytes.length);
					blockCont = baos.toByteArray();
					seek = file.length();		
					}
				/*
				 * 剩余文件长度大于块长度，取大小为blockLen的块，seek位置移动blockLen长度
				 */
				else if((file.length() - seek) > blockLen){
					tempbytes = new byte[blockLen];
					raf.seek(seek);
					raf.read(tempbytes);
					baos.write(tempbytes, 0, blockLen);
					blockCont = baos.toByteArray();
					seek += blockLen;
				}
				/*
				 * 文件分块结束
				 */
				else
					System.out.println("此文件已全部分块！");
			} catch (Exception e) {
				e.printStackTrace();
			} finally{
				if(baos != null){
					try {
						baos.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if(raf != null){
					try {
						raf.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}		
		}
		return blockCont;
	}
	/*
	 * 子包合并为文件
	 * @param 
	 * @param 
	 * @param 
	 * 
	 */
	
   public File PackCombine(int fileindex, int length, byte[] blockCont, String[] str){
	   
	   
	   
	  
	   return null;
   }

}
