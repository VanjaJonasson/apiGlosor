package com.example.apiGlosor.repositories;

import com.example.apiGlosor.entities.Category;
import com.example.apiGlosor.entities.Glosa;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Integer> {
    @Override
    List<Category> findAll();
}
