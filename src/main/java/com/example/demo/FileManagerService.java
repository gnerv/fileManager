package com.example.demo;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.gson.Gson;

@Service
public class FileManagerService implements IFileManager {

	@Value("${com.gnerv.fileManager.filePathPrefix}")
	private String filePathPrefix;
	
	@Override
	public FileManagerModel createAndSaveFile(MultipartFile file) {
		FileManagerModel fileManagerModel = new FileManagerModel();
		try {
			String uuid = UUID.randomUUID().toString().replaceAll("-", "");
			String md5DigestAsHex = DigestUtils.md5DigestAsHex(file.getBytes());
			String path = filePathPrefix + uuid;
			File filePath = new File(path);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			File fileObj = new File(filePath, uuid);
			Files.write(file.getBytes(), fileObj);
			
			fileManagerModel.setUuid(uuid);
			fileManagerModel.setName(file.getOriginalFilename());
			fileManagerModel.setPath(path);
			fileManagerModel.setMd5(md5DigestAsHex);
			
			Gson gson = new Gson();
			String json = gson.toJson(fileManagerModel);
			fileObj = new File(filePath, "fileManagerModel");
			Files.write(json.getBytes(), fileObj);
			
			return fileManagerModel;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return fileManagerModel;
	}

	@Override
	public File getFileByUuid(String uuid) {
		String path = filePathPrefix + uuid;
		try {
			File fileObj = new File(path, "fileManagerModel");
			if(fileObj.exists()) {
				List<String> readLines = Files.readLines(fileObj, Charsets.UTF_8);
				Gson gson = new Gson();
				FileManagerModel fromJson = gson.fromJson(readLines.get(0), FileManagerModel.class);
				String path2 = fromJson.getPath();
				File file = new File(path2, uuid);
				return file;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean checkFile(String uuid) {
		String path = filePathPrefix + uuid;
		File fileManagerModel = new File(path, "fileManagerModel");
		File fileManagerUuid = new File(path, uuid);
		if(fileManagerUuid.exists() && fileManagerUuid.isFile() && fileManagerModel.exists() && fileManagerModel.isFile()) {
			return true;
		}else {
			return false;
		}
	}

}
