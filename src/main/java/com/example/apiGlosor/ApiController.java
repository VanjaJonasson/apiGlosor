package com.example.apiGlosor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@RestController
public class ApiController {

    @Autowired
    GlosaRepository glosaRepository;

    @Autowired
    CategoryRepository catRepository;

    // nu utan kategorier
    //samma som i originalprogrammet med findall mot glosarepo
    @GetMapping("/glosor")
    public List<Glosa> glosor() {
        return (List) glosaRepository.findAll();
    }

    //returnerar alla kategorier ink lista över glosor i respektive kategori
    //samma som i originalprogrammet
    @GetMapping("/categories")
    public List<Category> categories() {
        return (List) catRepository.findAll();
    }

    //returnerar en random glosa i en viss kategori
    @GetMapping("/cat/{num}")
    public Glosa cat(@PathVariable int num) {
        Random randNum = new Random();
        List<Glosa> listaCat = (List<Glosa>) glosaRepository.findGlosorwithCat(num);
        int randomInt = randNum.nextInt((listaCat.size()) - 1);
        Glosa g = listaCat.get(randomInt);
        System.out.println(g.getEng());
        return g;
    }

    //funkar
    @GetMapping("/glosa/{id}")
    public Optional<Glosa> glosa(@PathVariable int id) {
        return glosaRepository.findById(id);
    }

    //returnerar en kategori med alla glosor i den kategorin som en array-variabel
    @GetMapping("/category/{id}")
    public Category category(@PathVariable int id) {
        return catRepository.findById(id).get();
    }

    @PostMapping("/glosa/{cat}")
    public Glosa post(@RequestBody Glosa glosa, @PathVariable int cat) {  //måste skicka med kategorin i request bodyn???
        Category category = catRepository.findById(cat).get();
        glosa.setCategory(category);
       //Category.addGLosa(glosa); behövs inte
        return glosaRepository.save(glosa);
    }

    @PutMapping("/glosa/{cat}")
    public Glosa put(@PathVariable int cat, @RequestBody Glosa glosa) {
        /*if (glosa.getId() == null) {
            glosa.setId(id);
        }
         */
        Category category = catRepository.findById(cat).get();
        glosa.setCategory(category);
        //category.addGLosa(glosa);
        return glosaRepository.save(glosa);
    }

    @DeleteMapping("/glosa/{id}")
    public void delete(@PathVariable int id) {
        glosaRepository.delete(glosaRepository.findById(id).get());
    }
}
