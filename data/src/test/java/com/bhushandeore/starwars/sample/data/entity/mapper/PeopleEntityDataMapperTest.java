package com.bhushandeore.starwars.sample.data.entity.mapper;

import com.bhushandeore.starwars.sample.data.entity.PeopleEntity;
import com.bhushandeore.starwars.sample.domain.People;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class PeopleEntityDataMapperTest {

  private static final int FAKE_USER_ID = 123;
  private static final String FAKE_FULLNAME = "Tony Stark";

  private PeopleEntityDataMapper peopleEntityDataMapper;

  @Before
  public void setUp() throws Exception {
    peopleEntityDataMapper = new PeopleEntityDataMapper();
  }

  @Test
  public void testTransformPeopleEntity() {
    PeopleEntity peopleEntity = createFakePeopleEntity();
    People people = peopleEntityDataMapper.transform(peopleEntity);

    assertThat(people, is(instanceOf(People.class)));
    assertThat(people.id, is(FAKE_USER_ID));
    assertThat(people.name, is(FAKE_FULLNAME));
  }

  @Test
  public void testTransformPeopleEntityCollection() {
    PeopleEntity mockPeopleEntityOne = mock(PeopleEntity.class);
    PeopleEntity mockPeopleEntityTwo = mock(PeopleEntity.class);

    List<PeopleEntity> peopleEntityList = new ArrayList<PeopleEntity>(5);
    peopleEntityList.add(mockPeopleEntityOne);
    peopleEntityList.add(mockPeopleEntityTwo);

    Collection<People> peopleCollection = peopleEntityDataMapper.transform(peopleEntityList);

    assertThat(peopleCollection.toArray()[0], is(instanceOf(People.class)));
    assertThat(peopleCollection.toArray()[1], is(instanceOf(People.class)));
    assertThat(peopleCollection.size(), is(2));
  }

  private PeopleEntity createFakePeopleEntity() {
    PeopleEntity peopleEntity = new PeopleEntity();
    peopleEntity.id = FAKE_USER_ID;
    peopleEntity.name = FAKE_FULLNAME;

    return peopleEntity;
  }
}
