package com.example.apiGlosor.repositories;

import com.example.apiGlosor.entities.Glosa;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface GlosaRepository extends CrudRepository<Glosa, Integer> {


    @Query("Select g from Glosa g JOIN g.category c WHERE c.id = ?1")
    Iterable<Glosa> findGlosorwithCat(Integer id);

    @Override
    List<Glosa> findAll();



/*
    @Query("Select g from Glosa g JOIN fetch g.category c WHERE g.id =:id")
    List<Glosa> findGlosorwithCat(@Param("id") Integer id);
      */


}
