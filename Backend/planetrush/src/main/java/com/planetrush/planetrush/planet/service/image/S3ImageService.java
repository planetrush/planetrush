package com.planetrush.planetrush.planet.service.image;

import org.springframework.web.multipart.MultipartFile;

import com.planetrush.planetrush.planet.service.dto.FileMetaInfo;

public interface S3ImageService {

	FileMetaInfo uploadPlanetImg(MultipartFile file, long memberId);

	FileMetaInfo uploadStandardVerificationImg(MultipartFile file, long memberId);

	/**
	 * 파일 확장자 추출
	 * @param fileName 파일명
	 * @return 추출된 확장자 반환
	 */
	default String getFileExtension(String fileName) {
		if (fileName == null || fileName.isEmpty() || !fileName.contains(".")) {
			return "";
		}
		return fileName.substring(fileName.lastIndexOf(".") + 1);
	}

}