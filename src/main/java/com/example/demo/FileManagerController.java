package com.example.demo;

import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiOperation;

@RestController
public class FileManagerController {
	
	@Autowired
	FileManagerService fileManagerService;

	@ApiOperation(value="上传文件接口", notes="")
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
	
	@ApiOperation(value="下载文件接口", notes="通过文件UUID值（唯一）下载文件")
	@RequestMapping(value="/downLoadFileForSingle")
	private FileManagerR downLoadFileForSingle(@RequestParam("uuid") String uuid, HttpServletResponse response) {
		
		try {
			//检查文件是否存在
			boolean checkFile = fileManagerService.checkFile(uuid);
			if(!checkFile) {
				return FileManagerR.ok("false");
			}
			//获取文件
			File fileByUuid = fileManagerService.getFileByUuid(uuid);
			//设置文件为直接下载 不可在线打开
			response.setContentType("application/octet-stream");
			//设置文件名和编码
			String fileName = URLEncoder.encode(uuid, "UTF-8");
			response.setHeader( "Content-Disposition", "attachment;filename=" +fileName);

			FileCopyUtils.copy(new FileInputStream(fileByUuid), response.getOutputStream());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return FileManagerR.ok("false");
	}
	
}
