package com.bhushandeore.starwars.sample.domain.interactor;

import com.bhushandeore.starwars.sample.domain.People;
import com.bhushandeore.starwars.sample.domain.executor.PostExecutionThread;
import com.bhushandeore.starwars.sample.domain.executor.ThreadExecutor;
import com.bhushandeore.starwars.sample.domain.repository.PeopleRepository;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * This class is an implementation of {@link UseCase} that represents a use case for
 * retrieving data related to an specific {@link People}.
 */
public class GetPeopleDetails extends UseCase<People, GetPeopleDetails.Params> {

    private final PeopleRepository peopleRepository;

    @Inject
    GetPeopleDetails(PeopleRepository peopleRepository, ThreadExecutor threadExecutor,
                     PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.peopleRepository = peopleRepository;
    }

    @Override
    Observable<People> buildUseCaseObservable(Params params) {
        return this.peopleRepository.peopleDetails(params.id);
    }

    public static final class Params {

        private final int id;

        private Params(int id) {
            this.id = id;
        }

        public static Params forPeople(int id) {
            return new Params(id);
        }
    }
}
