package com.blog.api.services.impl;

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

import com.blog.api.services.FileService;

@Service
public class FileServiceImpl implements FileService {

	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {
		// taking file name.
		String name = file.getOriginalFilename();

		// random name generator.
		String randomID = UUID.randomUUID().toString();

		// extracting the extension of the coming file.
		String fileExtension = name.substring(name.lastIndexOf("."));
		System.out.println(fileExtension);

		// if fileExtension is .jpg or .png than only it will upload.
		if (fileExtension.equals(".png") || fileExtension.equals(".jpg")) {

			// creating random name for file
			String fileName = randomID.concat(fileExtension);
			System.out.println(fileName);

			// creating full path.
			String filePath = path + File.separator + fileName;

			// creating folder if not created.
			File f = new File(path);

			if (!f.exists()) {
				f.mkdir();
			}

			// coping file.
			Files.copy(file.getInputStream(), Paths.get(filePath));

			return fileName;
		} else {
			throw new IOException("Only .jpg and .png is allow!!");
		}

	}

	@Override
	public InputStream getResource(String path, String fileName) throws FileNotFoundException {
		String fullPath = path + File.separator + fileName; // here File.separator means "/".in some OS it's different
															// that's why we use File.separator.
		InputStream inputStream = new FileInputStream(fullPath);
		// DataBase Logic To Return InputStream.
		return inputStream;
	}

}
