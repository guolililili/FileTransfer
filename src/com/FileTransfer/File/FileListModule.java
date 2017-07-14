package com.FileTransfer.File;
import java.io.File;
import java.util.ArrayList;
/*
 * 从文件列表随机选取一个文件,返回该文件的文件名称
 */
public class FileListModule {
 	
	public File getRandomFile(ArrayList<File> fileList){//传入文件列表
	    int index = (int)(Math.random()*fileList.size());
		File file = fileList.get(index);//随机取出文件
		return file;//返回文件
	}

}
