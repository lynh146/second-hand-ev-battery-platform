package com.example.service;

import com.example.model.Admin;
import com.example.model.Listing;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface IListingService {

    Listing createListing(Listing listing);
    List<Listing> findAll();
    Listing findById(Long id);
    Listing updateListing(Long id, Listing listing);
    void deleteListing(Long id);
    void uploadImages(Long listingId, List<MultipartFile> files);

    List<Listing> searchListing(String keyword); 
    List<Listing> featured(int limit);         
    Listing getPublicDetail(Long id);           

    List<Listing> getPendingListings();     
    Listing approveListing(Long id, Admin admin, boolean approved);
    
    void deleteImage(Long id);

}
