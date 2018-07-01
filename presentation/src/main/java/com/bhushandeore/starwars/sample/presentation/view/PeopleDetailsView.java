package com.bhushandeore.starwars.sample.presentation.view;

import com.bhushandeore.starwars.sample.presentation.model.PeopleModel;

/**
 * Interface representing a View in a model view presenter (MVP) pattern.
 * In this case is used as a view representing a people details.
 */
public interface PeopleDetailsView extends LoadDataView {
  /**
   * Render a people in the UI.
   *
   * @param peopleModel The {@link PeopleModel} that will be shown.
   */
  void renderPeople(PeopleModel peopleModel);
}
