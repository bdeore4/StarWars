package com.bhushandeore.starwars.sample.presentation;

import android.app.Application;
import com.bhushandeore.starwars.sample.presentation.di.components.ApplicationComponent;
import com.bhushandeore.starwars.sample.presentation.di.components.DaggerApplicationComponent;
import com.bhushandeore.starwars.sample.presentation.di.modules.ApplicationModule;
import com.bhushandeore.starwars.sample.presentation.BuildConfig;
import com.squareup.leakcanary.LeakCanary;

/**
 * Android Main Application
 */
public class AndroidApplication extends Application {

  private ApplicationComponent applicationComponent;

  @Override public void onCreate() {
    super.onCreate();
    this.initializeInjector();
    this.initializeLeakDetection();
  }

  private void initializeInjector() {
    this.applicationComponent = DaggerApplicationComponent.builder()
        .applicationModule(new ApplicationModule(this))
        .build();
  }

  public ApplicationComponent getApplicationComponent() {
    return this.applicationComponent;
  }

  private void initializeLeakDetection() {
    if (BuildConfig.DEBUG) {
      LeakCanary.install(this);
    }
  }
}
