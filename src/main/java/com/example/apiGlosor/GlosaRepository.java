package com.example.apiGlosor;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GlosaRepository extends CrudRepository<Glosa, Integer> {


    @Query("Select g from Glosa g JOIN g.category c WHERE c.id = ?1")
    Iterable<Glosa> findGlosorwithCat(Integer id);


/*
    @Query("Select g from Glosa g JOIN fetch g.category c WHERE g.id =:id")
    List<Glosa> findGlosorwithCat(@Param("id") Integer id);
      */


}
