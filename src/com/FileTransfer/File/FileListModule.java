package com.FileTransfer.File;
import java.io.File;
import java.util.ArrayList;
/*
 * ���ļ��б����ѡȡһ���ļ�,���ظ��ļ����ļ�����
 */
public class FileListModule {
 	
	public File getRandomFile(ArrayList<File> fileList){//�����ļ��б�
	    int index = (int)(Math.random()*fileList.size());
		File file = fileList.get(index);//���ȡ���ļ�
		return file;//�����ļ�
	}

}
