package com.bhushandeore.starwars.sample.data.repository.datasource;

import com.bhushandeore.starwars.sample.data.cache.PeopleCache;
import com.bhushandeore.starwars.sample.data.entity.PeopleEntity;
import com.bhushandeore.starwars.sample.data.net.RestApi;
import io.reactivex.Observable;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CloudPeopleDataStoreTest {

  private static final int FAKE_PEOPLE_ID = 765;

  private CloudPeopleDataStore cloudPeopleDataStore;

  @Mock private RestApi mockRestApi;
  @Mock private PeopleCache mockPeopleCache;

  @Before
  public void setUp() {
    cloudPeopleDataStore = new CloudPeopleDataStore(mockRestApi, mockPeopleCache);
  }

  @Test
  public void testGetUserEntityListFromApi() {
    cloudPeopleDataStore.peopleEntityList(1);
    verify(mockRestApi).peopleEntityList(1);
  }

  @Test
  public void testGetUserEntityDetailsFromApi() {
    PeopleEntity fakeUserEntity = new PeopleEntity();
    Observable<PeopleEntity> fakeObservable = Observable.just(fakeUserEntity);
    given(mockRestApi.peopleEntityById(FAKE_PEOPLE_ID)).willReturn(fakeObservable);

    cloudPeopleDataStore.peopleEntityDetails(FAKE_PEOPLE_ID);

    verify(mockRestApi).peopleEntityById(FAKE_PEOPLE_ID);
  }
}
