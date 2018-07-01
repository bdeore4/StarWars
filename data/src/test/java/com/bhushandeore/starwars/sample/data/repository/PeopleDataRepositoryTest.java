package com.bhushandeore.starwars.sample.data.repository;

import com.bhushandeore.starwars.sample.data.entity.PeopleEntity;
import com.bhushandeore.starwars.sample.data.entity.mapper.PeopleEntityDataMapper;
import com.bhushandeore.starwars.sample.data.repository.datasource.PeopleDataStore;
import com.bhushandeore.starwars.sample.data.repository.datasource.PeopleDataStoreFactory;
import com.bhushandeore.starwars.sample.domain.People;

import io.reactivex.Observable;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class PeopleDataRepositoryTest {

  private static final int FAKE_PEOPLE_ID = 123;

  private PeopleDataRepository peopleDataRepository;

  @Mock private PeopleDataStoreFactory mockPeopleDataStoreFactory;
  @Mock private PeopleEntityDataMapper mockPeopleEntityDataMapper;
  @Mock private PeopleDataStore mockPeopleDataStore;
  @Mock private PeopleEntity mockUserEntity;
  @Mock private People mockUser;

  @Before
  public void setUp() {
    peopleDataRepository = new PeopleDataRepository(mockPeopleDataStoreFactory, mockPeopleEntityDataMapper);
    given(mockPeopleDataStoreFactory.create(anyInt())).willReturn(mockPeopleDataStore);
    given(mockPeopleDataStoreFactory.createCloudDataStore()).willReturn(mockPeopleDataStore);
  }

  @Test
  public void testGetUsersHappyCase() {
    List<PeopleEntity> peopleEntityList = new ArrayList<>();
    peopleEntityList.add(new PeopleEntity());
    given(mockPeopleDataStore.peopleEntityList(1)).willReturn(Observable.just(peopleEntityList));

    peopleDataRepository.peopleList(1);

    verify(mockPeopleDataStoreFactory).createCloudDataStore();
    verify(mockPeopleDataStore).peopleEntityList(1);
  }

  @Test
  public void testGetUserHappyCase() {
    PeopleEntity peopleEntity = new PeopleEntity();
    given(mockPeopleDataStore.peopleEntityDetails(FAKE_PEOPLE_ID)).willReturn(Observable.just(peopleEntity));
    peopleDataRepository.peopleDetails(FAKE_PEOPLE_ID);

    verify(mockPeopleDataStoreFactory).create(FAKE_PEOPLE_ID);
    verify(mockPeopleDataStore).peopleEntityDetails(FAKE_PEOPLE_ID);
  }
}
