package com.sami.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public class FileUpload {

	@Value("${uploadPath}")
	String uploadPath;

	protected String upload(MultipartFile file) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-");
		String date = dateFormat.format(new Date());
		String name = date + file.getOriginalFilename().replace(' ', '-');
		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();
				File dir = new File(uploadPath + "/");
				if (!dir.exists())
					dir.mkdirs();
				File serverFile = new File(dir + File.separator + name);
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();
				return name;
			} catch (Exception e) {
				return null;
			}
		}
		return null;
	}

	protected void uploadImage(MultipartFile file, HttpServletRequest request,String fileName)
			throws IOException {
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

		Iterator<String> it = multipartRequest.getFileNames();

		file = multipartRequest.getFile(it.next());

		byte[] bytes = file.getBytes();

		BufferedOutputStream stream = new BufferedOutputStream(
				new FileOutputStream(new File("/home/sami/upload/photo/" + fileName)));

		stream.write(bytes);

		stream.close();
	}
	
	protected void updateImage(MultipartFile file, HttpServletRequest request,String fileName)
			throws IOException {
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

		Iterator<String> it = multipartRequest.getFileNames();

		MultipartFile multipartFile = multipartRequest.getFile(it.next());

		Files.delete(Paths.get("/home/sami/upload/photo/" + fileName));

		byte[] bytes = multipartFile.getBytes();
		
		BufferedOutputStream stream = new BufferedOutputStream(
				new FileOutputStream(new File("/home/sami/upload/photo/" + fileName)));
		
		stream.write(bytes);
		
		stream.close();
	}
}
