package com.example.apiGlosor.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

//import javax.persistence.*;
//when upgrading to springboot 3.0.0 changed from javax to jakarta
//https://stackoverflow.com/questions/73350585/upgrade-from-spring-boot-2-7-2-to-spring-boot-3-0-0-snapshot-java-package-java
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
/*@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
 */
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) //have changed between IDENTITY and AUTO.
    private Integer id;
    private String name;

    //@OneToMany(mappedBy = "category", cascade=CascadeType.ALL)
    //@JsonManagedReference
    @OneToMany(mappedBy = "category", cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    //changed to be able to update category. found this article helpful: https://stackoverflow.com/questions/49592081/jpa-detached-entity-passed-to-persist-nested-exception-is-org-hibernate-persis
    //changed from CascadeType.all
    //added fetch = FetchType.LAZY
    @JsonIgnore
    private List<Glosa> glosor = new ArrayList<>(); // glosor = list of words i glossary
    public Category() {
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<Glosa> getGlosor() {
        return glosor;
    }
    public void setGlosor(List<Glosa> glosor) {
        this.glosor = glosor;
    }
    public void addGLosa(Glosa glosa) {
        glosor.add(glosa);
    }
}

