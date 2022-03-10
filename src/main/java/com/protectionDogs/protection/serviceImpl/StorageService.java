package com.protectionDogs.protection.serviceImpl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;

@Service
public class StorageService {

	private Logger log = LoggerFactory.getLogger(ServiceLayer.class);

	@Value("${bucketNameImages}")
	private String imagebucketName;

	@Value("${bucketNameVideos}")
	private String videobucketName;

	@Autowired
	private AmazonS3 s3Client;

	public String uploadFile(MultipartFile file, boolean isVideo, String entityId) {
		File fileObj = convertMultiPartFileToFile(file);
		String fileName;
		if (isVideo)
			fileName = System.currentTimeMillis() + "_" + entityId + "_" + file.getOriginalFilename()
			      + (file.getOriginalFilename().endsWith(".mp4") ? "" : ".mp4");
		else
			fileName = System.currentTimeMillis() + "_" + entityId + "_"
			      + file.getOriginalFilename().substring(file.getOriginalFilename().length() / 2)
			      + (file.getOriginalFilename().endsWith(".jpg") || file.getOriginalFilename().endsWith(".png") ? ""
			            : ".jpg");

		s3Client.putObject(new PutObjectRequest(isVideo ? videobucketName : imagebucketName, fileName, fileObj)
		      .withCannedAcl(CannedAccessControlList.PublicRead));
		String url = s3Client.getUrl(isVideo ? videobucketName : imagebucketName, fileName).toString();
		fileObj.delete();
		return url;
	}

	public byte[] downloadFile(String fileName, boolean isVideo) {
		S3Object s3Object = s3Client.getObject(isVideo ? videobucketName : imagebucketName, fileName);
		S3ObjectInputStream inputStream = s3Object.getObjectContent();
		try {
			byte[] content = IOUtils.toByteArray(inputStream);
			return content;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Boolean deleteFile(String fileName, boolean isVideo) {
		s3Client.deleteObject(isVideo ? videobucketName : imagebucketName, fileName);
		return true;
	}

	private File convertMultiPartFileToFile(MultipartFile file) {
		File convertedFile = new File(file.getOriginalFilename());
		try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
			fos.write(file.getBytes());
		} catch (IOException e) {
			log.error("Error converting multipartFile to file", e);
		}
		return convertedFile;
	}
}