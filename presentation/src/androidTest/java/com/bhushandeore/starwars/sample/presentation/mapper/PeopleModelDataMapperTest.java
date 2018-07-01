package com.bhushandeore.starwars.sample.presentation.mapper;

import com.bhushandeore.starwars.sample.domain.People;
import com.bhushandeore.starwars.sample.presentation.model.PeopleModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import junit.framework.TestCase;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;

public class PeopleModelDataMapperTest extends TestCase {

  private static final int FAKE_PEOPLE_ID = 123;
  private static final String FAKE_FULL_NAME = "Tony Stark";

  private PeopleModelDataMapper peopleModelDataMapper;

  @Override protected void setUp() throws Exception {
    super.setUp();
    peopleModelDataMapper = new PeopleModelDataMapper();
  }

  public void testTransformPeople() {
    People people = createFakePeople();
    PeopleModel peopleModel = peopleModelDataMapper.transform(people);

    assertThat(peopleModel, is(instanceOf(PeopleModel.class)));
    assertThat(peopleModel.id, is(FAKE_PEOPLE_ID));
    assertThat(peopleModel.name, is(FAKE_FULL_NAME));
  }

  public void testTransformPeopleCollection() {
    People mockPeopleOne = mock(People.class);
    People mockPeopleTwo = mock(People.class);

    List<People> peopleList = new ArrayList<People>(5);
    peopleList.add(mockPeopleOne);
    peopleList.add(mockPeopleTwo);

    Collection<PeopleModel> peopleModelList = peopleModelDataMapper.transform(peopleList);

    assertThat(peopleModelList.toArray()[0], is(instanceOf(PeopleModel.class)));
    assertThat(peopleModelList.toArray()[1], is(instanceOf(PeopleModel.class)));
    assertThat(peopleModelList.size(), is(2));
  }

  private People createFakePeople() {
    People people = new People();
    people.name = FAKE_FULL_NAME;
    return people;
  }
}
