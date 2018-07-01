package com.bhushandeore.starwars.sample.presentation.mapper;

import com.bhushandeore.starwars.sample.domain.People;
import com.bhushandeore.starwars.sample.presentation.di.PerActivity;
import com.bhushandeore.starwars.sample.presentation.model.PeopleModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import javax.inject.Inject;

/**
 * Mapper class used to transform {@link People} (in the domain layer) to {@link PeopleModel} in the
 * presentation layer.
 */
@PerActivity
public class PeopleModelDataMapper {

  @Inject
  public PeopleModelDataMapper() {}

  /**
   * Transform a {@link People} into an {@link PeopleModel}.
   *
   * @param people Object to be transformed.
   * @return {@link PeopleModel}.
   */
  public PeopleModel transform(People people) {
    if (people == null) {
      throw new IllegalArgumentException("Cannot transform a null value");
    }
    final PeopleModel peopleModel = new PeopleModel();
      peopleModel.id = Integer.parseInt(people.url.replaceAll("[\\D]", ""));
      peopleModel.height = people.height;
      peopleModel.name = people.name;
      peopleModel.mass = people.mass;
      peopleModel.url = people.url;
      peopleModel.created = people.created;

    return peopleModel;
  }

  /**
   * Transform a Collection of {@link People} into a Collection of {@link PeopleModel}.
   *
   * @param peopleCollection Objects to be transformed.
   * @return List of {@link PeopleModel}.
   */
  public Collection<PeopleModel> transform(Collection<People> peopleCollection) {
    Collection<PeopleModel> peopleModelsCollection;

    if (peopleCollection != null && !peopleCollection.isEmpty()) {
        peopleModelsCollection = new ArrayList<>();
      for (People people : peopleCollection) {
          peopleModelsCollection.add(transform(people));
      }
    } else {
        peopleModelsCollection = Collections.emptyList();
    }

    return peopleModelsCollection;
  }
}
