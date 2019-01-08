package com.example.arkadiuszwochniak.domowe.di.module;

import android.content.Context;

import com.example.arkadiuszwochniak.domowe.di.qualifier.ActivityContext;
import com.example.arkadiuszwochniak.domowe.di.scopes.ActivityScope;
import com.example.arkadiuszwochniak.domowe.ui.FavouriteActivity;
import com.example.arkadiuszwochniak.domowe.ui.MainActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class FavouriteActivityContextModule {
    private FavouriteActivity favouriteActivity;

    public Context context;

    public FavouriteActivityContextModule(FavouriteActivity favouriteActivity) {
        this.favouriteActivity = favouriteActivity;
        context = favouriteActivity;
    }

    @Provides
    @ActivityScope
    public FavouriteActivity providesFavouriteActivity() {

        return favouriteActivity;
    }

    @Provides
    @ActivityScope
    @ActivityContext
    public Context provideContext() {
        return context;
    }
}
