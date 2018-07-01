package com.bhushandeore.starwars.sample.data.entity.mapper;

import com.bhushandeore.starwars.sample.data.entity.PeopleEntity;
import com.google.gson.JsonSyntaxException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collection;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class PeopleEntityJsonMapperTest {

    private static final String JSON_RESPONSE_PEOPLE_DETAILS = "{\n"
            + "    \"id\": 1,\n"
            + "    \"url\": \"https://swapi.co/api/people/1/\",\n"
            + "    \"name\": \"Luke Skywalker\",\n"
            + "}";

    private static final String JSON_RESPONSE_PEOPLE_COLLECTION = "[{\n"
            + "    \"id\": 1,\n"
            + "    \"url\": \"https://swapi.co/api/people/1/\",\n"
            + "    \"name\": \"Luke Skywalker\",\n"
            + "}, {\n"
            + "    \"id\": 2,\n"
            + "    \"url\": \"https://swapi.co/api/people/2/\",\n"
            + "    \"name\": \"C-3PO\",\n"
            + "}]";

    private PeopleEntityJsonMapper peopleEntityJsonMapper;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        peopleEntityJsonMapper = new PeopleEntityJsonMapper();
    }

    @Test
    public void testTransformPeopleEntityHappyCase() {
        PeopleEntity peopleEntity = peopleEntityJsonMapper.transformPeopleEntity(JSON_RESPONSE_PEOPLE_DETAILS);

        assertThat(peopleEntity.id, is(1));
        assertThat(peopleEntity.name, is(equalTo("Luke Skywalker")));
        assertThat(peopleEntity.url, is(equalTo("https://swapi.co/api/people/1/")));
    }

    @Test
    public void testTransformPeopleEntityCollectionHappyCase() {
        Collection<PeopleEntity> peopleEntityCollection =
                peopleEntityJsonMapper.transformPeopleEntityCollection(
                        JSON_RESPONSE_PEOPLE_COLLECTION);

        assertThat(((PeopleEntity) peopleEntityCollection.toArray()[0]).id, is(1));
        assertThat(((PeopleEntity) peopleEntityCollection.toArray()[1]).id, is(2));
        assertThat(peopleEntityCollection.size(), is(2));
    }

    @Test
    public void testTransformPeopleEntityNotValidResponse() {
        expectedException.expect(JsonSyntaxException.class);
        peopleEntityJsonMapper.transformPeopleEntity("ironman");
    }

    @Test
    public void testTransformPeopleEntityCollectionNotValidResponse() {
        expectedException.expect(JsonSyntaxException.class);
        peopleEntityJsonMapper.transformPeopleEntityCollection("Tony Stark");
    }
}
