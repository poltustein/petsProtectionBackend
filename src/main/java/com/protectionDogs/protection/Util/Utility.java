package com.protectionDogs.protection.Util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;

public class Utility {
	
	public static Gson gson = new Gson(); 
	
	public static Boolean writeFile(File f, MultipartFile mf) {
		InputStream fileinputstream = null;
		FileOutputStream fo = null;
		BufferedOutputStream bo = null;
		Boolean uploadStatus = false;
		
		if(mf==null || mf.getSize()<=0L) {
			return uploadStatus;
		}
		
		try {
			fileinputstream = mf.getInputStream();
			f.getParentFile().mkdir();
			fo = new FileOutputStream(f);
			bo = new BufferedOutputStream(fo);
			byte[] b = new byte[32 * 1024];
			int bytesRead = -1;
			if (fileinputstream != null) {
				while ((bytesRead = fileinputstream.read(b)) != -1) {
					bo.write(b, 0, bytesRead);
				}
				uploadStatus = true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			bo.close();
			fo.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return uploadStatus;
	}
	
	

}
