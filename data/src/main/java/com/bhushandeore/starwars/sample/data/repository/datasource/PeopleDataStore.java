package com.bhushandeore.starwars.sample.data.repository.datasource;

import com.bhushandeore.starwars.sample.data.entity.PeopleEntity;
import io.reactivex.Observable;
import java.util.List;

/**
 * Interface that represents a data store from where data is retrieved.
 */
public interface PeopleDataStore {
  /**
   * Get an {@link Observable} which will emit a List of {@link PeopleEntity}.
   */
  Observable<List<PeopleEntity>> peopleEntityList(final int page);

  /**
   * Get an {@link Observable} which will emit a {@link PeopleEntity} by its id.
   *
   * @param id The id to retrieve user data.
   */
  Observable<PeopleEntity> peopleEntityDetails(final int id);
}
