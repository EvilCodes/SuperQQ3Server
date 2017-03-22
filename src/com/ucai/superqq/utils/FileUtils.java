package com.ucai.superqq.utils;

import java.io.File;

public class FileUtils {
	
	/**
	 * 修改本地缓存的图片名称
	 * @param context
	 * @param oldImgName
	 * @param newImgName
	 */
	public static void renameImageFileName(String path,String oldImgName,String newImgName){
		File oldFile=new File(path,oldImgName);
		File newFile=new File(path,newImgName);
		boolean isRename = oldFile.renameTo(newFile);
		System.out.println("修改图片文件名成功:"+isRename);
	}
}
