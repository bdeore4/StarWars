package com.bhushandeore.starwars.sample.data.repository;

import com.bhushandeore.starwars.sample.data.entity.mapper.PeopleEntityDataMapper;
import com.bhushandeore.starwars.sample.data.repository.datasource.PeopleDataStore;
import com.bhushandeore.starwars.sample.data.repository.datasource.PeopleDataStoreFactory;
import com.bhushandeore.starwars.sample.domain.People;
import com.bhushandeore.starwars.sample.domain.repository.PeopleRepository;

import io.reactivex.Observable;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * {@link PeopleRepository} for retrieving user data.
 */
@Singleton
public class PeopleDataRepository implements PeopleRepository {

  private final PeopleDataStoreFactory peopleDataStoreFactory;
  private final PeopleEntityDataMapper peopleEntityDataMapper;

  /**
   * Constructs a {@link PeopleRepository}.
   *
   * @param dataStoreFactory A factory to construct different data source implementations.
   * @param peopleEntityDataMapper {@link PeopleEntityDataMapper}.
   */
  @Inject
  PeopleDataRepository(PeopleDataStoreFactory dataStoreFactory,
                       PeopleEntityDataMapper peopleEntityDataMapper) {
    this.peopleDataStoreFactory = dataStoreFactory;
    this.peopleEntityDataMapper = peopleEntityDataMapper;
  }

  @Override public Observable<List<People>> peopleList(int page) {
    //we always get all users from the cloud
    final PeopleDataStore peopleDataStore = this.peopleDataStoreFactory.createCloudDataStore();
    return peopleDataStore.peopleEntityList(page).map(this.peopleEntityDataMapper::transform);
  }

  @Override public Observable<People> peopleDetails(int id) {
    final PeopleDataStore peopleDataStore = this.peopleDataStoreFactory.create(id);
    return peopleDataStore.peopleEntityDetails(id).map(this.peopleEntityDataMapper::transform);
  }
}
