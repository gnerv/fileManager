package com.example.demo;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

public interface IFileManager {
	/**
	 * 创建一个文件保存到服务器
	 * @param MultipartFile
	 * @return 返回 FileManagerModel
	 */
	FileManagerModel createAndSaveFile(MultipartFile file);

	/**
	 * 通过UUID从服务器上获取文件
	 * @param uuid
	 * @return	返回 File
	 */
	File getFileByUuid(String uuid);
}
