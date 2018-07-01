package com.bhushandeore.starwars.sample.data.repository.datasource;

import com.bhushandeore.starwars.sample.data.ApplicationTestCase;
import com.bhushandeore.starwars.sample.data.cache.PeopleCache;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.robolectric.RuntimeEnvironment;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

public class PeopleDataStoreFactoryTest extends ApplicationTestCase {

  private static final int FAKE_PEOPLE_ID = 11;

  private PeopleDataStoreFactory peopleDataStoreFactory;

  @Mock private PeopleCache mockPeopleCache;

  @Before
  public void setUp() {
    peopleDataStoreFactory = new PeopleDataStoreFactory(RuntimeEnvironment.application, mockPeopleCache);
  }

  @Test
  public void testCreateDiskDataStore() {
    given(mockPeopleCache.isCached(FAKE_PEOPLE_ID)).willReturn(true);
    given(mockPeopleCache.isExpired()).willReturn(false);

    PeopleDataStore peopleDataStore = peopleDataStoreFactory.create(FAKE_PEOPLE_ID);

    assertThat(peopleDataStore, is(notNullValue()));
    assertThat(peopleDataStore, is(instanceOf(DiskPeopleDataStore.class)));

    verify(mockPeopleCache).isCached(FAKE_PEOPLE_ID);
    verify(mockPeopleCache).isExpired();
  }

  @Test
  public void testCreateCloudDataStore() {
    given(mockPeopleCache.isExpired()).willReturn(true);
    given(mockPeopleCache.isCached(FAKE_PEOPLE_ID)).willReturn(false);

    PeopleDataStore peopleDataStore = peopleDataStoreFactory.create(FAKE_PEOPLE_ID);

    assertThat(peopleDataStore, is(notNullValue()));
    assertThat(peopleDataStore, is(instanceOf(CloudPeopleDataStore.class)));

    verify(mockPeopleCache).isExpired();
  }
}
