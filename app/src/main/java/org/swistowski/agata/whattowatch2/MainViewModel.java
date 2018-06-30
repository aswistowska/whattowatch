package org.swistowski.agata.whattowatch2;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import org.swistowski.agata.whattowatch2.database.AppDatabase;
import org.swistowski.agata.whattowatch2.database.FavoriteEntry;

import java.util.List;

public class MainViewModel extends AndroidViewModel{

    private LiveData<List<FavoriteEntry>> favorite;

    public MainViewModel(@NonNull Application application) {
        super(application);
        AppDatabase database = AppDatabase.getInstance(this.getApplication());
        favorite = database.favoriteDao().loadAllFavorites();
    }

    public LiveData<List<FavoriteEntry>> getFavorite() {
        return favorite;
    }
}
