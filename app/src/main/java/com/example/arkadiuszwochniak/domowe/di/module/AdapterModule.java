package com.example.arkadiuszwochniak.domowe.di.module;

import com.example.arkadiuszwochniak.domowe.ui.MainActivity;
import com.example.arkadiuszwochniak.domowe.adapter.RecyclerViewAdapter;
import com.example.arkadiuszwochniak.domowe.di.scopes.ActivityScope;

import dagger.Module;
import dagger.Provides;

@Module(includes = {MainActivityContextModule.class})
public class AdapterModule {

    @Provides
    @ActivityScope
    public RecyclerViewAdapter getStarWarsPeopleLIst(RecyclerViewAdapter.ClickListener clickListener) {
        return new RecyclerViewAdapter(clickListener);
    }

    @Provides
    @ActivityScope
    public RecyclerViewAdapter.ClickListener getClickListener(MainActivity mainActivity) {
        return mainActivity;
    }
}

