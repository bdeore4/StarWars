package com.bhushandeore.starwars.sample.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * People Entity used in the data layer.
 */
public class PeopleEntity {

  @SerializedName("id")
  public int id;

  @SerializedName("name")
  public String name;

  @SerializedName("height")
  public String height;

  @SerializedName("mass")
  public String mass;

  @SerializedName("created")
  public String created;

  @SerializedName("url")
  public String url;

  public PeopleEntity() {
    //empty
  }
}
