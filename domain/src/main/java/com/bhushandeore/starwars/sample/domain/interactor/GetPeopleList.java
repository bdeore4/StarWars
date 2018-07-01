package com.bhushandeore.starwars.sample.domain.interactor;

import com.bhushandeore.starwars.sample.domain.People;
import com.bhushandeore.starwars.sample.domain.executor.PostExecutionThread;
import com.bhushandeore.starwars.sample.domain.executor.ThreadExecutor;
import com.bhushandeore.starwars.sample.domain.repository.PeopleRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * This class is an implementation of {@link UseCase} that represents a use case for
 * retrieving a collection of all {@link People}.
 */
public class GetPeopleList extends UseCase<List<People>, GetPeopleList.Params> {

    private final PeopleRepository peopleRepository;

    @Inject
    GetPeopleList(PeopleRepository peopleRepository, ThreadExecutor threadExecutor,
                  PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.peopleRepository = peopleRepository;
    }

    @Override
    Observable<List<People>> buildUseCaseObservable(Params params) {
        return this.peopleRepository.peopleList(params.page);
    }

    public static final class Params {

        private final int page;

        private Params(int page) {
            this.page = page;
        }

        public static Params forPeople(int page) {
            return new Params(page);
        }
    }
}
