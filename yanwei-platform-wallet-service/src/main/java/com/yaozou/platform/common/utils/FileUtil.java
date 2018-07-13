package com.yaozou.platform.common.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

/**
 * 文件上传工具类 
 * @author luojianhong
 * @version $Id: FileUtil.java, v 0.1 2017年10月10日 上午11:13:37 luojianhong Exp $
 */
public class FileUtil {

	public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception {
		File targetFile = new File(filePath);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		FileOutputStream out = new FileOutputStream(filePath + fileName);
		out.write(file);
		out.flush();
		out.close();
	}

	public static boolean deleteFile(String fileName) {
		File file = new File(fileName);
		// 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
		if (file.exists() && file.isFile()) {
			if (file.delete()) {
				System.out.println("删除单个文件" + fileName + "成功！");
				return true;
			} else {
				System.out.println("删除单个文件" + fileName + "失败！");
				return false;
			}
		} else {
			System.out.println("删除单个文件失败：" + fileName + "不存在！");
			return false;
		}
	}

	public static String RenameToUUID(String fileName) {
		return UUID.randomUUID() + "." + fileName.substring(fileName.lastIndexOf(".") + 1);
	}
}
