package com.example.apiGlosor.controller;

import com.example.apiGlosor.entities.Category;
import com.example.apiGlosor.entities.Glosa;
import com.example.apiGlosor.repositories.GlosaRepository;
import com.example.apiGlosor.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;


@RestController
public class ApiController {

    @Autowired
    ApiService apiService;

    //to try postman
    @Autowired
    GlosaRepository glosaRepository;

    //list of glossary
    //without categories depending on @JsonBackReference and @JsonManagedReference annotations in Entity classes.
    @GetMapping("/glosor")
    public List<Glosa> glosor() {
        return (List) apiService.findAllGlosor();
    }

    //returns all categories
    //incl. list of glossary in each category depending on @JsonBackReference and @JsonManagedReference annotations in Entity classes.
    @GetMapping("/categories")
    public List<Category> categories() {
        return (List) apiService.findAllCategories();
    }

    //returns a random "glosa" in a specific category
    @GetMapping("/cat/{num}")
    public Glosa cat(@PathVariable int num) {
        Glosa glosa = apiService.getRandomGlosainCat(num);
        return glosa;
    }

    //returns glosa by id
    @GetMapping("/glosa/{id}")
    public Optional<Glosa> glosa(@PathVariable int id) {
        return apiService.findGlosaById(id);
    }

    //returns a category
    //with all "glosor" in that category as an array attribute depending on @JsonBackReference and @JsonManagedReference annotations in Entity classes.
    @GetMapping("/category/{id}")
    public Category category(@PathVariable int id) {
        return apiService.findCategoryById(id);
    }

    @PostMapping("/glosa/{cat}")
    public Glosa post(@RequestBody Glosa glosa, @PathVariable int cat) {  //have to send category in request body?
        return apiService.save(glosa, cat);
    }

    /*
    @PutMapping("/glosa/{cat}")
    public Glosa put(@RequestBody Glosa glosa, @PathVariable int cat) {
        return apiService.save(glosa, cat);
    }
     */

    //doesn´t check if glosa exists and creates a new record if it doesn´t
    @PutMapping("/glosa/{cat}")
    public Glosa put(@RequestBody Glosa glosa, @PathVariable int cat) {
        return apiService.save(glosa, cat);
    }

    //use this put in order not to create a resource that doesn´t exist
    //https://www.springboottutorial.com/spring-boot-crud-rest-service-with-jpa-hibernate
    @PutMapping("/glosa/{cat}/{id}")
    public ResponseEntity<Object> updateGlosa(@RequestBody Glosa glosa, @PathVariable int cat, @PathVariable int id) {

        Optional<Glosa> glosaOptional = glosaRepository.findById(id);

        if (glosaOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        else {
            glosa.setId(id); //needed if id is not sent in response body not using form in "consumingApiGlosor
            apiService.save(glosa, cat);
            return ResponseEntity.noContent().build();
        }

    }

    @DeleteMapping("/glosa/{id}")
    public void delete(@PathVariable int id) {
        apiService.delete(id);
    }

    }



