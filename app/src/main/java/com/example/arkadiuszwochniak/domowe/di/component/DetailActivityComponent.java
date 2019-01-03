package com.example.arkadiuszwochniak.domowe.di.component;

import com.example.arkadiuszwochniak.domowe.ui.DetailActivity;
import com.example.arkadiuszwochniak.domowe.di.scopes.ActivityScope;

import dagger.Component;

@Component(dependencies = ApplicationComponent.class)
@ActivityScope
public interface DetailActivityComponent {

    void inject(DetailActivity detailActivity);
}