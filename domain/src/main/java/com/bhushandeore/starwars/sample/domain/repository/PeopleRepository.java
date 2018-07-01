package com.bhushandeore.starwars.sample.domain.repository;

import com.bhushandeore.starwars.sample.domain.People;

import io.reactivex.Observable;
import java.util.List;

/**
 * Interface that represents a Repository for getting {@link People} related data.
 */
public interface PeopleRepository {
  /**
   * Get an {@link Observable} which will emit a List of {@link People}.
   */
  Observable<List<People>> peopleList(final int page);

  /**
   * Get an {@link Observable} which will emit a {@link People}.
   *
   * @param id The people id used to retrieve people data.
   */
  Observable<People> peopleDetails(final int id);
}
