package com.bhushandeore.starwars.sample.presentation.di.components;

import android.content.Context;
import com.bhushandeore.starwars.sample.domain.executor.PostExecutionThread;
import com.bhushandeore.starwars.sample.domain.executor.ThreadExecutor;
import com.bhushandeore.starwars.sample.domain.repository.PeopleRepository;
import com.bhushandeore.starwars.sample.presentation.di.modules.ApplicationModule;
import com.bhushandeore.starwars.sample.presentation.view.activity.BaseActivity;
import dagger.Component;
import javax.inject.Singleton;

/**
 * A component whose lifetime is the life of the application.
 */
@Singleton // Constraints this component to one-per-application or unscoped bindings.
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
  void inject(BaseActivity baseActivity);

  //Exposed to sub-graphs.
  Context context();
  ThreadExecutor threadExecutor();
  PostExecutionThread postExecutionThread();
  PeopleRepository peopleRepository();
}
