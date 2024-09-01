package com.blog.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileServiceImpl implements FileService {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(FileServiceImpl.class);

	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {

		String originalFilename = file.getOriginalFilename();
		
		if(originalFilename == null || originalFilename.isEmpty()) {
			LOGGER.error("File name is invalid: {}", originalFilename);
			throw new IOException("Invalid File name");
		}
		
		String randomId = UUID.randomUUID().toString();
		String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
		String newFilename = randomId.concat(fileExtension);
		String filePath = path + File.separator + newFilename;
		
		File directory = new File(path);
		if(!directory.exists()) {
			LOGGER.info("Directory does not exist. Creating directory at path: {}", path);
			Boolean dirCreated = directory.mkdir();
			
			if(!dirCreated) {
				LOGGER.error("Failed to create directory at path: {}", path);
				throw new IOException("Failed to create directory at path: " + path); 
			}
		}
		
		
		try {
			Files.copy(file.getInputStream(), Paths.get(filePath));
			LOGGER.info("File successfully uploaded: {}", newFilename);
		} catch (IOException e) {
			LOGGER.error("Error occured while uploading the file: {}", newFilename, e);
			throw e;
		}
		
		return newFilename;
	}

	@Override
	public InputStream getResource(String path, String fileName) throws FileNotFoundException {
		String fullPath = path + File.separator + fileName;
		LOGGER.info("Fetching resource from path: {}", fullPath);
		
		try {
			InputStream inputStream = new FileInputStream(fullPath);
			LOGGER.info("Resource found: {}", fullPath);
			return inputStream;
		} catch (FileNotFoundException fne) {
			LOGGER.error("Resource not found at path: {}", fullPath, fne);
			throw fne;
		}
	}
}
