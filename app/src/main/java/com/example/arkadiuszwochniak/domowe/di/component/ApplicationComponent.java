package com.example.arkadiuszwochniak.domowe.di.component;

import android.content.Context;


import com.example.arkadiuszwochniak.domowe.MyApplication;
import com.example.arkadiuszwochniak.domowe.di.module.ContextModule;
import com.example.arkadiuszwochniak.domowe.di.module.RetrofitModule;
import com.example.arkadiuszwochniak.domowe.di.qualifier.ApplicationContext;
import com.example.arkadiuszwochniak.domowe.di.scopes.ApplicationScope;
import com.example.arkadiuszwochniak.domowe.retrofit.APIInterface;

import dagger.Component;

@ApplicationScope
@Component(modules = {ContextModule.class, RetrofitModule.class})
public interface ApplicationComponent {

    public APIInterface getApiInterface();

    @ApplicationContext
    public Context getContext();

    public void injectApplication(MyApplication myApplication);
}
