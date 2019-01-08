package com.example.arkadiuszwochniak.domowe.di.module;

import com.example.arkadiuszwochniak.domowe.adapter.RecyclerViewAdapter;
import com.example.arkadiuszwochniak.domowe.adapter.RecyclerViewAdapterFav;
import com.example.arkadiuszwochniak.domowe.di.scopes.ActivityScope;
import com.example.arkadiuszwochniak.domowe.ui.FavouriteActivity;
import com.example.arkadiuszwochniak.domowe.ui.MainActivity;

import dagger.Module;
import dagger.Provides;

@Module(includes = {FavouriteActivityContextModule.class})
public class AdapterModuleFav {

    @Provides
    @ActivityScope
    public RecyclerViewAdapterFav getStarWarsPeopleLIst(RecyclerViewAdapterFav.ClickListener clickListener) {
        return new RecyclerViewAdapterFav(clickListener);
    }

    @Provides
    @ActivityScope
    public RecyclerViewAdapterFav.ClickListener getClickListener(FavouriteActivity favouriteActivity) {
        return favouriteActivity;
    }
}
