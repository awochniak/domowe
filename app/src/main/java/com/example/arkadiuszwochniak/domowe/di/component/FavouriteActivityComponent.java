package com.example.arkadiuszwochniak.domowe.di.component;


import android.content.Context;

import com.example.arkadiuszwochniak.domowe.di.module.AdapterModule;
import com.example.arkadiuszwochniak.domowe.di.module.AdapterModuleFav;
import com.example.arkadiuszwochniak.domowe.di.qualifier.ActivityContext;
import com.example.arkadiuszwochniak.domowe.di.scopes.ActivityScope;
import com.example.arkadiuszwochniak.domowe.ui.FavouriteActivity;
import com.example.arkadiuszwochniak.domowe.ui.MainActivity;

import dagger.Component;

@ActivityScope
@Component(modules = AdapterModuleFav.class, dependencies = ApplicationComponent.class)
public interface FavouriteActivityComponent {

    @ActivityContext
    Context getContext();


    void injectFavouriteActivity(FavouriteActivity favouriteActivity);
}