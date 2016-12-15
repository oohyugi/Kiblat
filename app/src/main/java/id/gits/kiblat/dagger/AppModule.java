package id.gits.kiblat.dagger;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yogi on 02/12/16.
 */
@Module
public class AppModule {

    Application mApplication;

    public AppModule(Application application){
        mApplication =application;

    }

    @Provides
    @Singleton
    Application providesApplication(){
        return mApplication;
    }

}