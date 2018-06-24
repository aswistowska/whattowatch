package org.swistowski.agata.whattowatch2.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface FavoriteDao {

    @Query("SELECT * FROM favorite")
    List<FavoriteEntry> loadAllFavorites();

    @Query("SELECT * FROM favorite WHERE id = :id")
    List<FavoriteEntry> getFavorite(int id);

    @Insert
    void insertFavorite(FavoriteEntry favoriteEntry);

    @Delete
    void deleteFavorite(FavoriteEntry favoriteEntry);
}
