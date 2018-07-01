package com.bhushandeore.starwars.sample.data.cache;

import com.bhushandeore.starwars.sample.data.entity.PeopleEntity;
import io.reactivex.Observable;

/**
 * An interface representing a people Cache.
 */
public interface PeopleCache {
  /**
   * Gets an {@link Observable} which will emit a {@link PeopleEntity}.
   *
   * @param id The user id to retrieve data.
   */
  Observable<PeopleEntity> get(final int id);

  /**
   * Puts and element into the cache.
   *
   * @param peopleEntity Element to insert in the cache.
   */
  void put(PeopleEntity peopleEntity);

  /**
   * Checks if an element (People) exists in the cache.
   *
   * @param id The id used to look for inside the cache.
   * @return true if the element is cached, otherwise false.
   */
  boolean isCached(final int id);

  /**
   * Checks if the cache is expired.
   *
   * @return true, the cache is expired, otherwise false.
   */
  boolean isExpired();

  /**
   * Evict all elements of the cache.
   */
  void evictAll();
}
