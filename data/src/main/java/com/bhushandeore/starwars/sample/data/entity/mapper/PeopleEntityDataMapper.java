package com.bhushandeore.starwars.sample.data.entity.mapper;

import com.bhushandeore.starwars.sample.data.entity.PeopleEntity;
import com.bhushandeore.starwars.sample.domain.People;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Mapper class used to transform {@link PeopleEntity} (in the data layer) to {@link People} in the
 * domain layer.
 */
@Singleton
public class PeopleEntityDataMapper {

    @Inject
    PeopleEntityDataMapper() {
    }

    /**
     * Transform a {@link PeopleEntity} into an {@link People}.
     *
     * @param peopleEntity Object to be transformed.
     * @return {@link People} if valid {@link PeopleEntity} otherwise null.
     */
    public People transform(PeopleEntity peopleEntity) {
        People people = null;
        if (peopleEntity != null) {
            people = new People();
            people.id = peopleEntity.id;
            people.name = peopleEntity.name;
            people.height = peopleEntity.height;
            people.mass = peopleEntity.mass;
            people.url = peopleEntity.url;
            people.created = peopleEntity.created;
        }
        return people;
    }

    /**
     * Transform a List of {@link PeopleEntity} into a Collection of {@link People}.
     *
     * @param peopleEntityCollection Object Collection to be transformed.
     * @return {@link People} if valid {@link PeopleEntity} otherwise null.
     */
    public List<People> transform(Collection<PeopleEntity> peopleEntityCollection) {
        final List<People> peopleList = new ArrayList<>(20);
        for (PeopleEntity peopleEntity : peopleEntityCollection) {
            final People people = transform(peopleEntity);
            if (people != null) {
                peopleList.add(people);
            }
        }
        return peopleList;
    }
}
