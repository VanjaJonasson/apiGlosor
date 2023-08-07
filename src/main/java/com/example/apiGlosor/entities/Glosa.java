package com.example.apiGlosor.entities;

//import javax.persistence.*;
//when upgrading to springboot 3.0.0 changed from javax to jakarta
//https://stackoverflow.com/questions/73350585/upgrade-from-spring-boot-2-7-2-to-spring-boot-3-0-0-snapshot-java-package-java
import jakarta.persistence.*;


// This tells Hibernate to make a table out of this class
@Entity
/*@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
 */
public class Glosa { //Swedish word "glosa" is a word in a glossary
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) //changed b/w IDENTITY and AUTO.
    private Integer id;
    private String eng;
    private String swe;

    //@JsonBackReference //categories not showing in json at http://localhost:8081/glosor when commented out
    @ManyToOne
    @JoinColumn(name="category_id", nullable=false) ////it seems to work without this line as well
    private Category category;

    public Glosa() {
    }

    public Glosa(Integer id, String eng, String swe) {
        this.id = id;
        this.eng = eng;
        this.swe = swe;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEng() {
        return eng;
    }

    public void setEng(String eng) {
        this.eng = eng;
    }

    public String getSwe() {
        return swe;
    }

    public void setSwe(String swe) {
        this.swe = swe;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
