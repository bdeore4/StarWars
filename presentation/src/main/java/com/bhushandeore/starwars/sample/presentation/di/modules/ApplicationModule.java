package com.bhushandeore.starwars.sample.presentation.di.modules;

import android.content.Context;

import com.bhushandeore.starwars.sample.data.cache.PeopleCache;
import com.bhushandeore.starwars.sample.data.cache.PeopleCacheImpl;
import com.bhushandeore.starwars.sample.data.executor.JobExecutor;
import com.bhushandeore.starwars.sample.data.repository.PeopleDataRepository;
import com.bhushandeore.starwars.sample.domain.executor.PostExecutionThread;
import com.bhushandeore.starwars.sample.domain.executor.ThreadExecutor;
import com.bhushandeore.starwars.sample.domain.repository.PeopleRepository;
import com.bhushandeore.starwars.sample.presentation.AndroidApplication;
import com.bhushandeore.starwars.sample.presentation.UIThread;

import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

/**
 * Dagger module that provides objects which will live during the application lifecycle.
 */
@Module
public class ApplicationModule {
  private final AndroidApplication application;

  public ApplicationModule(AndroidApplication application) {
    this.application = application;
  }

  @Provides @Singleton Context provideApplicationContext() {
    return this.application;
  }

  @Provides @Singleton ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
    return jobExecutor;
  }

  @Provides @Singleton PostExecutionThread providePostExecutionThread(UIThread uiThread) {
    return uiThread;
  }

  @Provides @Singleton
  PeopleCache providePeopleCache(PeopleCacheImpl peopleCache) {
    return peopleCache;
  }

  @Provides @Singleton
  PeopleRepository provideUserRepository(PeopleDataRepository peopleDataRepository) {
    return peopleDataRepository;
  }
}
