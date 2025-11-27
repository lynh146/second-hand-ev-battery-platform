package com.example.service.impl;

import com.example.model.Admin;
import com.example.model.Listing;
import com.example.model.ListingImage;
import com.example.repository.*;
import com.example.service.IListingService;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Primary
@RequiredArgsConstructor
@Transactional
public class ListingServiceImpl implements IListingService {

    private final ListingRepository listingRepository;
    private final ListingImageRepository listingImageRepository;
    private final VehicleRepository vehicleRepository;
    private final BatteryRepository batteryRepository;

    private final Path uploadDir = Paths.get("uploads");

    @Override
    public Listing createListing(Listing listing) {
        if (listing.getCreatedAt() == null)
            listing.setCreatedAt(LocalDateTime.now());

        if (listing.getStatus() == null)
            listing.setStatus("PENDING");

        return listingRepository.save(listing);
    }

    @Override
    public List<Listing> findAll() {
        return listingRepository.findAll();
    }

    @Override
    public Listing findById(Long id) {
        return listingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Listing not found: " + id));
    }

    @Override
    public Listing updateListing(Long id, Listing updated) {
        Listing existing = findById(id);
        existing.setTitle(updated.getTitle());
        existing.setDescription(updated.getDescription());
        existing.setPrice(updated.getPrice());
        return listingRepository.save(existing);
    }

    @Override
    public void deleteListing(Long id) {
        Listing listing = findById(id);

        List<ListingImage> imgs = listingImageRepository.findByListing_ListingID(id);
        for (ListingImage img : imgs) {
            try {
                Files.deleteIfExists(Paths.get("uploads")
                        .resolve(img.getImageUrl().replace("/uploads/", "")));
            } catch (Exception ignored) {}
        }
        listingImageRepository.deleteAll(imgs);

        if (listing.getVehicle() != null)
            vehicleRepository.delete(listing.getVehicle());

        if (listing.getBattery() != null)
            batteryRepository.delete(listing.getBattery());

        listingRepository.delete(listing);
    }

    @Override
    public void uploadImages(Long listingId, List<MultipartFile> files) {
        Listing listing = findById(listingId);

        try {
            if (!Files.exists(uploadDir))
                Files.createDirectories(uploadDir);

            for (MultipartFile file : files) {
                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                Path target = uploadDir.resolve(fileName);

                Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

                ListingImage img = ListingImage.builder()
                        .listing(listing)
                        .imageUrl("/uploads/" + fileName)
                        .sortOrder(1)
                        .createdAt(LocalDateTime.now())
                        .build();

                listingImageRepository.save(img);
            }

        } catch (Exception e) {
            throw new RuntimeException("Cannot upload images", e);
        }
    }

    @Override
    public void deleteImage(Long imgId) {
        ListingImage img = listingImageRepository.findById(imgId).orElse(null);
        if (img == null) return;

        try {
            Files.deleteIfExists(Paths.get("uploads")
                    .resolve(img.getImageUrl().replace("/uploads/", "")));
        } catch (Exception ignored) {}

        listingImageRepository.delete(img);
    }

    @Override
    public List<Listing> searchListing(String keyword) {
        String k = (keyword == null) ? "" : keyword.trim();
        if (k.isEmpty()) {
            Pageable p = PageRequest.of(0, 20,
                    Sort.by(Sort.Direction.DESC, "createdAt"));
            return listingRepository.findByStatus("PUBLIC", p).getContent();
        }
        return listingRepository.findByStatusAndTitleContainingIgnoreCase("PUBLIC", k);
    }

    @Override
    public List<Listing> featured(int limit) {
        Pageable p = PageRequest.of(0, Math.max(1, limit),
                Sort.by(Sort.Direction.DESC, "createdAt"));
        return listingRepository.findByStatus("PUBLIC", p).getContent();
    }

    @Override
    public Listing getPublicDetail(Long id) {
        return listingRepository.findById(id).orElse(null);
    }

    @Override
    public List<Listing> getPendingListings() {
        return listingRepository.findByStatusAndApprovedByIsNull("PENDING");
    }

    @Override
    public Listing approveListing(Long id, Admin admin, boolean approved) {
        Listing listing = findById(id);
        if (approved) {
            listing.setStatus("PUBLIC");
            listing.setApprovedBy(admin);
        } else {
            listing.setStatus("REJECTED");
            listing.setApprovedBy(null);
        }
        return listingRepository.save(listing);
    }
}
