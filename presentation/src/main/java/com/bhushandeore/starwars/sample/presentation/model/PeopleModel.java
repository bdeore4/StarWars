package com.bhushandeore.starwars.sample.presentation.model;

import com.bhushandeore.starwars.sample.presentation.constant.PresentationConstant;

/**
 * Class that represents a people in the presentation layer.
 */
public class PeopleModel {

  public int id;
  public String name;
  public String height;
  public String mass;
  public String created;
  public String url;

  // Non API Members
  public String itemViewType = PresentationConstant.ITEM_VIEW_TYPE_PEOPLE;
}
