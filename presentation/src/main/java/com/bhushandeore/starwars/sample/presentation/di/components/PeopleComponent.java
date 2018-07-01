package com.bhushandeore.starwars.sample.presentation.di.components;

import com.bhushandeore.starwars.sample.presentation.di.PerActivity;
import com.bhushandeore.starwars.sample.presentation.di.modules.ActivityModule;
import com.bhushandeore.starwars.sample.presentation.di.modules.PeopleModule;
import com.bhushandeore.starwars.sample.presentation.view.fragment.PeopleDetailsFragment;
import com.bhushandeore.starwars.sample.presentation.view.fragment.PeopleListFragment;
import dagger.Component;

/**
 * A scope {@link com.bhushandeore.starwars.sample.presentation.di.PerActivity} component.
 * Injects people specific Fragments.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, PeopleModule.class})
public interface PeopleComponent extends ActivityComponent {
  void inject(PeopleListFragment peopleListFragment);
  void inject(PeopleDetailsFragment peopleDetailsFragment);
}
