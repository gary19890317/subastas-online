package com.sales.online.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sales.online.model.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {

  @Query(
      value =
          "select * from item where datediff('hour', now(), created) <= 2 and status='ACTIVO' and name ilike %:itemName% or description ilike %:itemName%",
      nativeQuery = true)
  List<Item> findLatestItems(String itemName);

  @Query(
      value = "select * from item where datediff('hour', now(), created) <= 2 and status='ACTIVO'",
      nativeQuery = true)
  List<Item> findLatestItems();

  @Query(value = "select * from item where ranking > 2 and status='ACTIVO'", nativeQuery = true)
  List<Item> findBestRatedItems();

  @Query(
      value =
          "select * from item where datediff('hour', now(), expiration_Date) <= 6 and status='ACTIVO'",
      nativeQuery = true)
  List<Item> findNextToFinishItems();

  @Query(
      value =
          "select * from item where datediff('second', now(), expiration_Date) <= 0 and status='ACTIVO'",
      nativeQuery = true)
  List<Item> findExpiredItems();

  @Query(value = "select * from item where status='EXPIRADO'", nativeQuery = true)
  List<Item> findInactiveItems();

  @Query(value = "select i from Item i where name=?1")
  Item itemNew(String name);
}
