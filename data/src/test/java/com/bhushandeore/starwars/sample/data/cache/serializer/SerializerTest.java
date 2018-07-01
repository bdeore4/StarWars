package com.bhushandeore.starwars.sample.data.cache.serializer;

import com.bhushandeore.starwars.sample.data.entity.PeopleEntity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class SerializerTest {

  private static final String JSON_RESPONSE = "{\n"
      + "    \"id\": 1,\n"
      + "    \"url\": \"https://swapi.co/api/people/1/\",\n"
      + "    \"name\": \"Luke Skywalker\",\n"
      + "}";

  private Serializer serializer;

  @Before
  public void setUp() {
    serializer = new Serializer();
  }

  @Test
  public void testSerializeHappyCase() {
    final PeopleEntity peopleEntityOne = serializer.deserialize(JSON_RESPONSE, PeopleEntity.class);
    final String jsonString = serializer.serialize(peopleEntityOne, PeopleEntity.class);
    final PeopleEntity peopleEntityTwo = serializer.deserialize(jsonString, PeopleEntity.class);

    assertThat(peopleEntityOne.id, is(peopleEntityTwo.id));
    assertThat(peopleEntityOne.name, is(equalTo(peopleEntityTwo.name)));
    assertThat(peopleEntityOne.url, is(peopleEntityTwo.url));
  }

  @Test
  public void testDesearializeHappyCase() {
    final PeopleEntity peopleEntity = serializer.deserialize(JSON_RESPONSE, PeopleEntity.class);

    assertThat(peopleEntity.id, is(1));
    assertThat(peopleEntity.name, is("Luke Skywalker"));
    assertThat(peopleEntity.url, is("https://swapi.co/api/people/1/"));
  }
}
