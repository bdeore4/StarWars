package com.bhushandeore.starwars.sample.data.repository.datasource;

import android.content.Context;
import android.support.annotation.NonNull;

import com.bhushandeore.starwars.sample.data.cache.PeopleCache;
import com.bhushandeore.starwars.sample.data.entity.mapper.PeopleEntityJsonMapper;
import com.bhushandeore.starwars.sample.data.net.RestApi;
import com.bhushandeore.starwars.sample.data.net.RestApiImpl;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Factory that creates different implementations of {@link PeopleDataStore}.
 */
@Singleton
public class PeopleDataStoreFactory {

  private final Context context;
  private final PeopleCache userCache;

  @Inject
  PeopleDataStoreFactory(@NonNull Context context, @NonNull PeopleCache userCache) {
    this.context = context.getApplicationContext();
    this.userCache = userCache;
  }

  /**
   * Create {@link PeopleDataStore} from a people id.
   */
  public PeopleDataStore create(int id) {
    PeopleDataStore peopleDataStore;

    if (!this.userCache.isExpired() && this.userCache.isCached(id)) {
      peopleDataStore = new DiskPeopleDataStore(this.userCache);
    } else {
      peopleDataStore = createCloudDataStore();
    }

    return peopleDataStore;
  }

  /**
   * Create {@link PeopleDataStore} to retrieve data from the Cloud.
   */
  public PeopleDataStore createCloudDataStore() {
    final PeopleEntityJsonMapper userEntityJsonMapper = new PeopleEntityJsonMapper();
    final RestApi restApi = new RestApiImpl(this.context, userEntityJsonMapper);
    return new CloudPeopleDataStore(restApi, this.userCache);
  }
}
