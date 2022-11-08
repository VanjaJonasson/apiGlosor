package com.example.apiGlosor.service;

import com.example.apiGlosor.entities.Category;
import com.example.apiGlosor.entities.Glosa;
import com.example.apiGlosor.repositories.CategoryRepository;
import com.example.apiGlosor.repositories.GlosaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class ApiService {

    @Autowired
    GlosaRepository glosaRepository;

    @Autowired
    CategoryRepository categoryRepository;

    public List<Glosa> findAllGlosor() {
        return (List) glosaRepository.findAll();
    }

    public List<Category> findAllCategories() {
        return (List) categoryRepository.findAll();
    }

    public Glosa getRandomGlosainCat(int num) {
        Random randNum = new Random();
        List<Glosa> listaCat = (List<Glosa>) glosaRepository.findGlosorwithCat(num);
        int randomInt = randNum.nextInt((listaCat.size()) - 1);
        Glosa glosa = listaCat.get(randomInt);
        return glosa;
    }

    public Optional<Glosa> findGlosaById(int id) {
        return glosaRepository.findById(id);
    }

    public Category findCategoryById(int id) {
        return categoryRepository.findById(id).get();
    }

    public Glosa save(Glosa glosa, int cat) {
        Category category = categoryRepository.findById(cat).get();
        glosa.setCategory(category);
        //Category.addGLosa(glosa); not needed
        return glosaRepository.save(glosa);
    }

    public void delete(int id) {
        glosaRepository.delete(glosaRepository.findById(id).get());
    };
}

