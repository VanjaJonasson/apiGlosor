package com.example.apiGlosor;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.lang.NonNull;

import javax.persistence.*;


// This tells Hibernate to make a table out of this class
@Entity
/*@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")

 */
public class Glosa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //ändrade från AUTO
    private Integer id;
    private String eng;
    private String swe;

    //@JsonBackReference //nåt hände när denna kommenterades bort
    @ManyToOne
    @JoinColumn(name="category_id", nullable=false) //det verkar funka utan denna rad
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
