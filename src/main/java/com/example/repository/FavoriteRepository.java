package com.example.repository;

import com.example.model.Favorite;
import com.example.model.Listing;
import com.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    boolean existsByUserAndListing(User user, Listing listing);

    Favorite findByUserAndListing(User user, Listing listing);

    List<Favorite> findByUser(User user);
}
