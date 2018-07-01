package com.bhushandeore.starwars.sample.data.repository.datasource;

import com.bhushandeore.starwars.sample.data.cache.PeopleCache;
import com.bhushandeore.starwars.sample.data.entity.PeopleEntity;
import io.reactivex.Observable;
import java.util.List;

/**
 * {@link PeopleDataStore} implementation based on file system data store.
 */
class DiskPeopleDataStore implements PeopleDataStore {

  private final PeopleCache userCache;

  /**
   * Construct a {@link PeopleDataStore} based file system data store.
   *
   * @param userCache A {@link PeopleCache} to cache data retrieved from the api.
   */
  DiskPeopleDataStore(PeopleCache userCache) {
    this.userCache = userCache;
  }

  @Override public Observable<List<PeopleEntity>> peopleEntityList(final int page) {
    //TODO: implement simple cache for storing/retrieving collections of users.
    throw new UnsupportedOperationException("Operation is not available!!!");
  }

  @Override public Observable<PeopleEntity> peopleEntityDetails(final int id) {
     return this.userCache.get(id);
  }
}
