package org.swistowski.agata.whattowatch2.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "favorite")
public class FavoriteEntry {

    @PrimaryKey
    private int id;

    public FavoriteEntry(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
