package com.example.apiGlosor.service;

import com.example.apiGlosor.GlosaAlreadyExistsException;
import com.example.apiGlosor.GlosaNotFoundException;
import com.example.apiGlosor.entities.Category;
import com.example.apiGlosor.entities.Glosa;
import com.example.apiGlosor.repositories.CategoryRepository;
import com.example.apiGlosor.repositories.GlosaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class ApiService {

    @Autowired
    GlosaRepository glosaRepository;

    @Autowired
    CategoryRepository categoryRepository;

/*    public List<Glosa> findAllGlosor() {
        return (List) glosaRepository.findAll();
    }*/

/*    public List<Category> findAllCategories() {
        return (List) categoryRepository.findAll();
    }*/

    public Glosa getRandomGlosainCat(int num) {
        //TODO throw exception if glosa in that category does not exist
        Random randNum = new Random();
        List<Glosa> listaCat = (List<Glosa>) glosaRepository.findGlosorwithCat(num);
        int randomInt = randNum.nextInt((listaCat.size()) - 1);
        Glosa glosa = listaCat.get(randomInt);
        return glosa;
    }

    public Glosa findGlosaById(int id) {
        return glosaRepository.findById(id)
                .orElseThrow(()-> new GlosaNotFoundException(id));
    }

    public Category findCategoryById(int id) {
        return categoryRepository.findById(id).orElseThrow(() -> new GlosaNotFoundException(id));
    }

    public ResponseEntity save(Glosa glosa, int cat) {
        //check if glosa with id exist to prevent update on save if id is sent in request body
        if(glosa.getId() != null && glosaRepository.existsById(glosa.getId())){
            throw new GlosaAlreadyExistsException(glosa.getId());
        }
        glosa.setCategory(categoryRepository.findById(cat).orElseThrow(() -> new GlosaNotFoundException(cat)));
        Glosa savedGlosa = glosaRepository.save(glosa);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedGlosa.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    public ResponseEntity update(Glosa glosa, int cat, int id) {
        Optional<Glosa> glosaOptional = glosaRepository.findById(id);

        System.out.println("empty: " + glosaOptional.isEmpty());

        if (glosaOptional.isEmpty()) {
            throw new GlosaNotFoundException(id);
        }
            Optional<Glosa> updatedGlosa = glosaRepository.findById(id).map(g -> {
                g.setCategory(categoryRepository.findById(cat).orElseThrow(() -> new GlosaNotFoundException(cat)));
                g.setEng(glosa.getEng());
                g.setSwe(glosa.getSwe());
                return glosaRepository.save(g);
            });

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(glosaRepository.findById(id)).toUri();
        return ResponseEntity.created(location).build();
    }


/*    public void delete(int id) {
        glosaRepository.deleteById(id);
    }*/
}

