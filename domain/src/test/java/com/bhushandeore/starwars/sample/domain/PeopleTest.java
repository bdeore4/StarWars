package com.bhushandeore.starwars.sample.domain;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PeopleTest {

    private static final int FAKE_PEOPLE_ID = 8;

    private People people;

    @Before
    public void setUp() {
        people = new People();
        people.id = FAKE_PEOPLE_ID;
    }

    @Test
    public void testPeopleConstructorHappyCase() {
        final int id = people.id;

        assertThat(id).isEqualTo(FAKE_PEOPLE_ID);
    }
}
