package com.qurepass.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.charset.StandardCharsets;
import javax.imageio.ImageIO;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class QRUtils {
	
	/**
	 * QR code 생성
	 * @param path
	 * @param name
	 * @param content
	 * @param imgType
	 * @param width
	 * @param height
	 * @return
	 * @throws Exception
	 * @author 최창현
	 */
	public File createQRimageFile(String path, String name, String content, String imgType, int width, int height) throws Exception {
		String qrData   = null;
		String fileName = null;
		
		/* 1. 폴더 미존재 시 폴더 생성 */
		File file = new File(path);
		if(!file.exists()) {
			file.mkdirs();
		}
		
		/* 2. QR code 정보 세팅 */
		fileName = path + name + "." + imgType;
		qrData = new String(content.getBytes(StandardCharsets.UTF_8.name()), StandardCharsets.ISO_8859_1.name());
		
		/* 3. QR code 생성 */
		QRCodeWriter writer = new QRCodeWriter();
		BitMatrix matrix =  writer.encode(qrData, BarcodeFormat.QR_CODE, width, height);
		BufferedImage bufimg = MatrixToImageWriter.toBufferedImage(matrix);
		
		/* 4. QR code 이미지 파일 생성 */
		File qrCode = new File(fileName);
		ImageIO.write(bufimg, imgType, qrCode);    // QR code 이미지로 덮어씌움
		
		return qrCode;
	}
	
	/**
	 * QR code 이미지 파일 삭제
	 * @param path
	 * @param name
	 * @return
	 * @author 최창현
	 */
	public boolean deleteFIle(String path, String name) {
		String filePath = path + name;
		
		File delFile = new File(filePath);
		
		if(delFile.exists()) {
			delFile.delete();
		} else {
			throw new RuntimeException("QR code 이미지 파일이 존재하지 않습니다.");
		}
		
		return true;
	}

}
