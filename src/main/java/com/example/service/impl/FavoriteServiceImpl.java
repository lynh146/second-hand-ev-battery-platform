package com.example.service.impl;

import com.example.model.Favorite;
import com.example.model.Listing;
import com.example.model.User;
import com.example.repository.FavoriteRepository;
import com.example.service.IFavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements IFavoriteService {

    private final FavoriteRepository favoriteRepo;

    @Override
    public boolean toggleFavorite(User user, Listing listing) {

        if (favoriteRepo.existsByUserAndListing(user, listing)) {
            Favorite f = favoriteRepo.findByUserAndListing(user, listing);
            if (f != null) favoriteRepo.delete(f);
            return false; 
        }

        Favorite fav = new Favorite();
        fav.setUser(user);
        fav.setListing(listing);
        favoriteRepo.save(fav);

        return true; 
    }

    @Override
    public boolean isFavorite(User user, Listing listing) {
        return favoriteRepo.existsByUserAndListing(user, listing);
    }

    @Override
    public List<Favorite> getUserFavorites(User user) {
        return favoriteRepo.findByUser(user)
                .stream()
                .filter(f -> {
                    Listing l = f.getListing();
                    return l != null && 
                        !"SOLD".equalsIgnoreCase(l.getStatus()) &&
                        !"INACTIVE".equalsIgnoreCase(l.getStatus());
                })
                .toList();
    }

}
