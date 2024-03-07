package com.blog.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileServiceImpl implements FileService {

	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {
		// TODO Auto-generated method stub

//		File name
		String name = file.getOriginalFilename();
		
//		Random name to generate file
		String randomId = UUID.randomUUID().toString();
		String fileName1 = randomId.concat(name.substring(name.lastIndexOf(".")));
		
//		Full Path
		String filePath = path + File.separator + fileName1;
		
//		Create Folder if not created
		File f = new File(path);
		if(!f.exists()) {
			f.mkdir();
		}
		
		// File Copy
		Files.copy(file.getInputStream(), Paths.get(filePath));
		
		
		return fileName1;
	}

	@Override
	public InputStream getResource(String path, String fileName) throws FileNotFoundException {
		// TODO Auto-generated method stub
		String fullPath = path + File.separator + fileName;
		InputStream is = new FileInputStream(fullPath);
		
		// db logic to return inputStream
		
		return is;
	}

}
