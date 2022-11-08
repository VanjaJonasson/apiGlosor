package com.example.apiGlosor.Controller;

import com.example.apiGlosor.Entities.Category;
import com.example.apiGlosor.Entities.Glosa;
import com.example.apiGlosor.Service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;


@RestController
public class ApiController {

    @Autowired
    ApiService apiService;

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

    //returms a random "glosa" in a specific category
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

    @PutMapping("/glosa/{cat}")
    public Glosa put(@PathVariable int cat, @RequestBody Glosa glosa) {
        return apiService.save(glosa, cat);
    }

    @DeleteMapping("/glosa/{id}")
    public void delete(@PathVariable int id) {
        apiService.delete(id);
    }
}
