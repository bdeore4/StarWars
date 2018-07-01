package com.bhushandeore.starwars.sample.presentation.view;

import com.bhushandeore.starwars.sample.presentation.model.PeopleModel;
import java.util.Collection;

/**
 * Interface representing a View in a model view presenter (MVP) pattern.
 * In this case is used as a view representing a list of {@link PeopleModel}.
 */
public interface PeopleListView extends LoadDataView {
  /**
   * Render a people list in the UI.
   *
   * @param peopleModelCollection The collection of {@link PeopleModel} that will be shown.
   */
  void renderPeopleList(Collection<PeopleModel> peopleModelCollection);

  /**
   * View a {@link PeopleModel} people/details.
   *
   * @param peopleModel The people that will be shown.
   */
  void viewPeople(PeopleModel peopleModel);

  void showViewRetry();
}
