package com.sunbeam.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.sunbeam.dto.ApiResponse;
import com.sunbeam.dto.TourPackageRespDTO;
import com.sunbeam.entities.TourPackage;

public interface TourPackageService {
	//ApiResponse saveTourPackage(TourPackageDTO tourPackageDTO);
	ApiResponse saveTourPackage(Long id,TourPackage tourPkg,String path, MultipartFile file) throws IOException;
	List<TourPackageRespDTO> getAllTourPackages();
	List<TourPackageRespDTO> getTourPackagesByGuideId(Long guideId);
	ApiResponse deleteTourPackage(Long id);
//	ApiResponse updateTourPackage(Long id, TourPackageDTO tourPackageDTO, MultipartFile imageFile) throws IOException;
//	ApiResponse uploadImage(Long id,String path, MultipartFile image) throws IOException;
	InputStream getimage(String path, String filename) throws FileNotFoundException;
}
