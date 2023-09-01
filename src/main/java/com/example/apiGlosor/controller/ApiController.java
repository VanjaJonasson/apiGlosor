package com.example.apiGlosor.controller;

import com.example.apiGlosor.entities.Category;
import com.example.apiGlosor.entities.Glosa;
import com.example.apiGlosor.repositories.CategoryRepository;
import com.example.apiGlosor.repositories.GlosaRepository;
import com.example.apiGlosor.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
public class ApiController {

    @Autowired
    ApiService apiService;

    @Autowired
    GlosaRepository glosaRepository;

    @Autowired
    CategoryRepository categoryRepository;

    //list of glossary
    //without categories depending on @JsonBackReference and @JsonManagedReference annotations in Entity classes.
    @GetMapping("/glosor")
    public List<Glosa> glosor() {
        return glosaRepository.findAll();
    }

    //returns all categories
    //incl. list of glossary in each category depending on @JsonBackReference and @JsonManagedReference annotations in Entity classes.
    @GetMapping("/categories")
    public List<Category> categories() {
        return categoryRepository.findAll();
    }

    //returns a random "glosa" in a specific category
    @GetMapping("/cat/{num}")
    public Glosa cat(@PathVariable int num) {
        return apiService.getRandomGlosainCat(num);
    }

    //returns glosa by id
    @GetMapping("/glosa/{id}")
    public Glosa glosa(@PathVariable int id) {
        return apiService.findGlosaById(id);
    }

    //returns a category
    //with all "glosor" in that category as an array attribute depending on @JsonBackReference and @JsonManagedReference annotations in Entity classes.
    @GetMapping("/category/{id}")
    public Category category(@PathVariable int id) {
        return apiService.findCategoryById(id);
    }

    @PostMapping("/glosa/{cat}")
    public ResponseEntity post(@RequestBody Glosa glosa, @PathVariable int cat) {  //category does not need to be sent in request body
        return apiService.save(glosa, cat);
    }


    //use this put in order not to create a resource that doesn´t exist
    //https://www.springboottutorial.com/spring-boot-crud-rest-service-with-jpa-hibernate
    @PutMapping("/glosa/{cat}/{id}")
    public ResponseEntity updateGlosa(@RequestBody Glosa glosa, @PathVariable int cat, @PathVariable int id) {
        return apiService.update(glosa, cat, id);
    }

    @DeleteMapping("/glosa/{id}")
    public ResponseEntity delete(@PathVariable int id) {
        glosaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    }



