package com.sunbeam.services;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import com.sunbeam.custom_exceptions.ResourceNotFoundException;
import com.sunbeam.daos.BookingDao;
import com.sunbeam.daos.TourPackageDao;
import com.sunbeam.daos.UserDao;
import com.sunbeam.dto.ApiResponse;
import com.sunbeam.dto.TourPackageRespDTO;
import com.sunbeam.entities.Booking;
import com.sunbeam.entities.TourPackage;
import com.sunbeam.entities.User;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;import java.util.stream.Collector;
import java.util.stream.Collectors;


@Service
@Transactional
public class TourPackageServiceImpl implements TourPackageService {
	
    @Autowired
    private TourPackageDao tourPackageDao;
    
    @Autowired
    private UserDao userDao;
    
    @Autowired
    private BookingDao bookingDao;

    @Autowired
    private ModelMapper modelMapper;
   

//    public ApiResponse saveTourPackage(TourPackageDTO tourPackageDTO) {
//    	User guid = userDao.findById(tourPackageDTO.getGuidId())
//    			.orElseThrow(() -> new ResourceNotFoundException("Invalid Guid id !!!!"));
//    	TourPackage tourPkg = modelMapper.map(tourPackageDTO, TourPackage.class);
//        guid.addTourPackage(tourPkg);
//        tourPackageDao.save(tourPkg);
//       
//        return new ApiResponse("Tour Package created successfully");
//    }
    
    public ApiResponse saveTourPackage(Long id,TourPackage tourPkg,String path, MultipartFile file) throws IOException {
    	
    	String name=file.getOriginalFilename();
		
		String randomID=UUID.randomUUID().toString();
		String filename=randomID.concat(name.substring(name.lastIndexOf('.')));
		
		String filepath=path+File.separator+filename;
		
		File f=new File(path);
		if(!f.exists())
		{
			f.mkdir();
		}
		
		Files.copy(file.getInputStream(), Paths.get(filepath));
		
		tourPkg.setImage(filename);
		User guid = userDao.findById(id)
	        		.orElseThrow(() -> new ResourceNotFoundException("Invalid TourGuid "));
	    guid.addTourPackage(tourPkg);
		tourPackageDao.save(tourPkg);
    	return new ApiResponse("Tour Package Added successfully");
    }
    

    public List<TourPackageRespDTO> getAllTourPackages() {
    	List<TourPackageRespDTO> tourPakgs = tourPackageDao.findAll()
    			.stream().map(tourPkg ->modelMapper.map(tourPkg, TourPackageRespDTO.class))
    			.collect(Collectors.toList());
    	
    	return tourPakgs; 
    }

    public List<TourPackageRespDTO> getTourPackagesByGuideId(Long guideId) {
        List<TourPackage> tourPackages = tourPackageDao.findByGuideId(guideId);
        if (tourPackages.isEmpty()) {
            throw new ResourceNotFoundException("No tour packages found for the guide");
        }
        return tourPackages.stream()
                           .map(tourPackage -> modelMapper.map(tourPackage, TourPackageRespDTO.class))
                           .collect(Collectors.toList());
    }

    public ApiResponse deleteTourPackage(Long id) {
        if(tourPackageDao.existsById(id)) {
        	TourPackage tourPkg = tourPackageDao.findById(id)
        	.orElseThrow(() -> new ResourceNotFoundException("Invalid package id !!!!"));
        	User guid = userDao.findById(tourPkg.getGuide().getId())
        	.orElseThrow(() -> new ResourceNotFoundException("Invalid user id !!!!"));
        	List<Booking> bookings = bookingDao.findByTourPackage(tourPkg);
        	guid.removeBookings(bookings);
        	guid.removeTourPackage(tourPkg);
        	
        	tourPackageDao.deleteById(id);
        	return new ApiResponse("Tour Package deleted successfully");
        }
        return new ApiResponse("Tour Package deleted failed");
    }

//    public ApiResponse updateTourPackage(Long id, TourPackageDTO tourPackageDTO, MultipartFile imageFile) throws IOException {
//        TourPackage tourPackage = tourPackageDao.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tour Package not found"));
//        modelMapper.map(tourPackageDTO, tourPackage);
//
//        if (imageFile != null && !imageFile.isEmpty()) {
//            tourPackage.setImage(imageFile.getBytes());
//        }
//
//        tourPackage = tourPackageDao.save(tourPackage);
//        return new ApiResponse("Tour Package updated successfully");
//    }
    
//    public ApiResponse uploadImage(Long id,String path, MultipartFile file) throws IOException{
//		String name=file.getOriginalFilename();
//		
//		String randomID=UUID.randomUUID().toString();
//		String filename=randomID.concat(name.substring(name.lastIndexOf('.')));
//		
//		String filepath=path+File.separator+filename;
//		
//		File f=new File(path);
//		if(!f.exists())
//		{
//			f.mkdir();
//		}
//		
//		Files.copy(file.getInputStream(), Paths.get(filepath));
//		TourPackage tourPkg = tourPackageDao.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tour Package not found"));
//		tourPkg.setImage(filename);
//		tourPackageDao.save(tourPkg);
//		return new ApiResponse("image uploaded..!");
//	}
    
    public InputStream getimage(String path, String filename) throws FileNotFoundException {
		String fullpath=path+File.separator+filename;
		InputStream s=new FileInputStream(fullpath);
		return s;
	}


	
}
