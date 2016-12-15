package id.gits.kiblat.dagger;

import android.app.Application;

/**
 * Created by yogi on 02/12/16.
 */
public class Injector {

    public static AppComponent mAppComponent;

    public static AppComponent initialize(Application application){
        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(application))
                .build();
        return mAppComponent;
    }

}