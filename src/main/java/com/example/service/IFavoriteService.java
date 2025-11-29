package com.example.service;

import com.example.model.Favorite;
import com.example.model.Listing;
import com.example.model.User;

import java.util.List;

public interface IFavoriteService {

    boolean toggleFavorite(User user, Listing listing);

    boolean isFavorite(User user, Listing listing);

    List<Favorite> getUserFavorites(User user);
}
