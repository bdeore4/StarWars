package com.bhushandeore.starwars.sample.data.repository.datasource;

import com.bhushandeore.starwars.sample.data.cache.PeopleCache;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DiskPeopleDataStoreTest {

  private static final int FAKE_PEOPLE_ID = 11;

  private DiskPeopleDataStore diskPeopleDataStore;

  @Mock private PeopleCache mockPeopleCache;

  @Rule public ExpectedException expectedException = ExpectedException.none();

  @Before
  public void setUp() {
    diskPeopleDataStore = new DiskPeopleDataStore(mockPeopleCache);
  }

  @Test
  public void testGetPeopleEntityListUnsupported() {
    expectedException.expect(UnsupportedOperationException.class);
    diskPeopleDataStore.peopleEntityList(1);
  }

  @Test
  public void testGetPeopleEntityDetailesFromCache() {
    diskPeopleDataStore.peopleEntityDetails(FAKE_PEOPLE_ID);
    verify(mockPeopleCache).get(FAKE_PEOPLE_ID);
  }
}
