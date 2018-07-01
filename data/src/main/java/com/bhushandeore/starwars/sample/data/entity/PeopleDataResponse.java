package com.bhushandeore.starwars.sample.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * People Entity used in the data layer.
 */
public class PeopleDataResponse {

  @SerializedName("count")
  public int count;

  @SerializedName("results")
  public List<PeopleEntity> results;

  public PeopleDataResponse() {
    //empty
  }
}
