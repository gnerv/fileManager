package com.example.demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileManagerController {
	
	@Autowired
	FileManagerService fileManagerService;

	@PostMapping(value="/upLoadFileForSingle")
	private FileManagerR upLoadFileForSingle(@RequestParam("file") MultipartFile file) {
		if (file.isEmpty()) {
			return FileManagerR.error("no file! please choose a file!");
		}
		if (file.getSize() / 1024 / 1024 > 5) {
			return FileManagerR.error("file size is too big! must be less than 5M!");
		}
		
		FileManagerModel createAndSaveFile = fileManagerService.createAndSaveFile(file);
		return FileManagerR.ok("true").put("fileManager", createAndSaveFile);
	}
	
	
	@RequestMapping(value="/downLoadFileForSingle")
	private FileManagerR downLoadFileForSingle(@RequestParam("uuid") String uuid, HttpServletResponse response) {
		
		try {
			response.setContentType("application/octet-stream");
			String fileName = URLEncoder.encode("1234567890呵呵呵呵额", "UTF-8");
			response.setHeader( "Content-Disposition", "attachment;filename=" +fileName);
			File fileByUuid = fileManagerService.getFileByUuid(uuid);
			FileCopyUtils.copy(new FileInputStream(fileByUuid), response.getOutputStream());
			return FileManagerR.ok("true");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return FileManagerR.ok("false");
	}
	
}
