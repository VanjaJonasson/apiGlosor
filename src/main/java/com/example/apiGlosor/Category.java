package com.example.apiGlosor;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
/*@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")

 */
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//ändrade från AUTO
    private Integer id;
    private String name;

    //@OneToMany(mappedBy = "category", cascade=CascadeType.ALL)
    //@JsonManagedReference
    @OneToMany(mappedBy = "category", cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    //ändrade så att det skulle gå att uppdatera kategori efter denna googling https://stackoverflow.com/questions/49592081/jpa-detached-entity-passed-to-persist-nested-exception-is-org-hibernate-persis
    //innan med CascadeType.all fungerade det inte att uppdatera kategorin
    //la till fetch = FetchType.LAZY efter video-tutorial
    @JsonIgnore
    private List<Glosa> glosor = new ArrayList<>();

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

