package com.bhushandeore.starwars.sample.data.entity.mapper;

import com.bhushandeore.starwars.sample.data.entity.PeopleDataResponse;
import com.bhushandeore.starwars.sample.data.entity.PeopleEntity;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;
import javax.inject.Inject;

/**
 * Class used to transform from Strings representing json to valid objects.
 */
public class PeopleEntityJsonMapper {

  private final Gson gson;

  @Inject
  public PeopleEntityJsonMapper() {
    this.gson = new Gson();
  }

  /**
   * Transform from valid json string to {@link PeopleEntity}.
   *
   * @param peopleJsonResponse A json representing a people profile.
   * @return {@link PeopleEntity}.
   * @throws JsonSyntaxException if the json string is not a valid json structure.
   */
  public PeopleEntity transformPeopleEntity(String peopleJsonResponse) throws JsonSyntaxException {
    final Type peopleEntityType = new TypeToken<PeopleEntity>() {}.getType();
    return this.gson.fromJson(peopleJsonResponse, peopleEntityType);
  }

  /**
   * Transform from valid json string to List of {@link PeopleEntity}.
   *
   * @param peopleListJsonResponse A json representing a collection of peoples.
   * @return List of {@link PeopleEntity}.
   * @throws JsonSyntaxException if the json string is not a valid json structure.
   */
  public List<PeopleEntity> transformPeopleEntityCollection(String peopleListJsonResponse)
      throws JsonSyntaxException {
    final Type listOfUserEntityType = new TypeToken<List<PeopleEntity>>() {}.getType();
    return this.gson.fromJson(peopleListJsonResponse, listOfUserEntityType);
  }


  public PeopleDataResponse transformPeopleDataResponse(String peopleJsonResponse) throws JsonSyntaxException {
    final Type peopleDataType = new TypeToken<PeopleDataResponse>() {}.getType();
    return this.gson.fromJson(peopleJsonResponse, peopleDataType);
  }
}
