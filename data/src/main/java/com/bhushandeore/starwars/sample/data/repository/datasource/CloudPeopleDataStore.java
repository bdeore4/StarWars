package com.bhushandeore.starwars.sample.data.repository.datasource;

import com.bhushandeore.starwars.sample.data.cache.PeopleCache;
import com.bhushandeore.starwars.sample.data.entity.PeopleEntity;
import com.bhushandeore.starwars.sample.data.net.RestApi;
import io.reactivex.Observable;
import java.util.List;

/**
 * {@link PeopleDataStore} implementation based on connections to the api (Cloud).
 */
class CloudPeopleDataStore implements PeopleDataStore {

  private final RestApi restApi;
  private final PeopleCache userCache;

  /**
   * Construct a {@link PeopleDataStore} based on connections to the api (Cloud).
   *
   * @param restApi The {@link RestApi} implementation to use.
   * @param userCache A {@link PeopleCache} to cache data retrieved from the api.
   */
  CloudPeopleDataStore(RestApi restApi, PeopleCache userCache) {
    this.restApi = restApi;
    this.userCache = userCache;
  }

  @Override public Observable<List<PeopleEntity>> peopleEntityList(final int page) {
    return this.restApi.peopleEntityList(page);
  }

  @Override public Observable<PeopleEntity> peopleEntityDetails(final int id) {
    return this.restApi.peopleEntityById(id).doOnNext(CloudPeopleDataStore.this.userCache::put);
  }
}
