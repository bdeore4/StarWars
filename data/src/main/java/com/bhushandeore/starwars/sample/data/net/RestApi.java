package com.bhushandeore.starwars.sample.data.net;

import com.bhushandeore.starwars.sample.data.entity.PeopleEntity;
import io.reactivex.Observable;
import java.util.List;

/**
 * RestApi for retrieving data from the network.
 */
public interface RestApi {
  String API_BASE_URL =
      "https://swapi.co/api/";

  /** Api url for getting all people */
  String API_URL_GET_PEOPLE_LIST = API_BASE_URL + "people?page=";

  /** Api url for getting a people detail*/
  String API_URL_GET_PEOPLE_DETAILS = API_BASE_URL + "people/";

  /**
   * Retrieves an {@link Observable} which will emit a List of {@link PeopleEntity}.
   */
  Observable<List<PeopleEntity>> peopleEntityList(final int page);

  /**
   * Retrieves an {@link Observable} which will emit a {@link PeopleEntity}.
   *
   * @param id The user id used to get user data.
   */
  Observable<PeopleEntity> peopleEntityById(final int id);
}
