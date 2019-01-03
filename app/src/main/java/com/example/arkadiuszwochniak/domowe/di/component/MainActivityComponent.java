package com.example.arkadiuszwochniak.domowe.di.component;

import android.content.Context;

import com.example.arkadiuszwochniak.domowe.di.module.AdapterModule;
import com.example.arkadiuszwochniak.domowe.di.qualifier.ActivityContext;
import com.example.arkadiuszwochniak.domowe.di.scopes.ActivityScope;
import com.example.arkadiuszwochniak.domowe.ui.MainActivity;

import dagger.Component;

@ActivityScope
@Component(modules = AdapterModule.class, dependencies = ApplicationComponent.class)
public interface MainActivityComponent {

    @ActivityContext
    Context getContext();


    void injectMainActivity(MainActivity mainActivity);
}