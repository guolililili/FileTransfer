package com.FileTransfer.File;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
public class FileModule {

	/*
	 * ���ļ��ְ�
	 * @param file �ļ�
	 * @param blockLen ����С
	 * @param seek λ��
	 * 
	 */
	public byte[] FileSplit(File file, int blockLen, long seek){
		byte[] blockCont = null; // �ӿ�����ֽ�����
		if(file.exists()){
			RandomAccessFile raf = null;
			ByteArrayOutputStream  baos = null;
			try {
				raf = new RandomAccessFile(file, "r");
				baos = new ByteArrayOutputStream();
				byte[] tempbytes = null;
				/*
				 * ʣ���ļ�����С�ڻ���ڿ鳤�ȣ��Զ�Ϊһ�飬seek�ƶ����ļ�ĩβλ��
				 */
				if((file.length() - seek) > 0 && (file.length() - seek) <= blockLen){
					tempbytes = new byte[(int) (file.length() - seek)];
					raf.read(tempbytes);
					baos.write(tempbytes, 0, tempbytes.length);
					blockCont = baos.toByteArray();
					seek = file.length();		
					}
				/*
				 * ʣ���ļ����ȴ��ڿ鳤�ȣ�ȡ��СΪblockLen�Ŀ飬seekλ���ƶ�blockLen����
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
				 * �ļ��ֿ����
				 */
				else
					System.out.println("���ļ���ȫ���ֿ飡");
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
	 * �Ӱ��ϲ�Ϊ�ļ�
	 * @param 
	 * @param 
	 * @param 
	 * 
	 */
	
   public File PackCombine(int fileindex, int length, byte[] blockCont, String[] str){
	   
	   
	   
	  
	   return null;
   }

}
