package com.bhushandeore.starwars.sample.data.exception;

/**
 * Exception throw by the application when a People search can't return a valid result.
 */
public class PeopleNotFoundException extends Exception {
  public PeopleNotFoundException() {
    super();
  }
}
