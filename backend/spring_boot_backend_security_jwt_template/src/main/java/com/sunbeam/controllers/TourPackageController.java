package com.sunbeam.controllers;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.engine.jdbc.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunbeam.entities.TourPackage;
import com.sunbeam.services.TourPackageService;

@RestController
@RequestMapping("/tour-packages")
public class TourPackageController {
    @Autowired
    private TourPackageService tourPackageService;
    
    @Autowired
    private ObjectMapper mapper;
    
    @Value("${project.image}")
    private String path;

//    @PostMapping
//    public ResponseEntity<?> createTourPackage(@RequestBody TourPackageDTO tourPakDto){
//        return ResponseEntity.status(HttpStatus.CREATED).body(tourPackageService.saveTourPackage(tourPakDto));
//    }

    @PostMapping("/{guidId}")
    public ResponseEntity<?> createTourPackage(@PathVariable Long guidId,@RequestParam("tourPkgData") String tourPkgData,@RequestParam("image") MultipartFile file) throws IOException{
    	TourPackage tourPkg = mapper.readValue(tourPkgData, TourPackage.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(tourPackageService.saveTourPackage(guidId,tourPkg,path,file));
    }
    
    @GetMapping
    public ResponseEntity<?> getAllTourPackages() {
        return ResponseEntity.ok(tourPackageService.getAllTourPackages());
    }

    @GetMapping("/{guideId}")
    public ResponseEntity<?> getTourPackageByGuideId(@PathVariable Long guideId) {
        return ResponseEntity.ok(tourPackageService.getTourPackagesByGuideId(guideId));
    }

//    @PutMapping("/{tourPkgId}")
//    public ResponseEntity<?> updateTourPackage(@PathVariable Long tourPkgId, @RequestParam("tourPackage") String tourPackageDTO, @RequestParam("image") MultipartFile imageFile) throws IOException {
//        TourPackageDTO dto = new ObjectMapper().readValue(tourPackageDTO, TourPackageDTO.class);
//        return ResponseEntity.ok(tourPackageService.updateTourPackage(tourPkgId, dto, imageFile));
//    }

    @DeleteMapping("/{tourPkgId}")
    public ResponseEntity<?> deleteTourPackage(@PathVariable Long tourPkgId) {
        return ResponseEntity.ok(tourPackageService.deleteTourPackage(tourPkgId));
    }
    
//    @PostMapping("/upload/{tourPkgId}")
//   	public ResponseEntity<?> fileUpload(@PathVariable Long tourPkgId,
//   	    @RequestParam("image") MultipartFile image
//   	) throws IOException {  
//   	    return ResponseEntity.ok(tourPackageService.uploadImage( tourPkgId ,path, image));
//   	}
       
    @GetMapping("/image/{imagename}")
   	public void getImage(
   			@PathVariable("imagename") String imagename,
   			HttpServletResponse response
   			) throws IOException
   	{
   		InputStream resource=tourPackageService.getimage(path,imagename);
   		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
   		StreamUtils.copy(resource,response.getOutputStream());
   	}
}
