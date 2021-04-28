package com.simplerestapi.entity;

import org.hibernate.annotations.Type;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
public class Characters {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    private String name;

    @NonNull
    private String description;

    @NonNull
    @Type(type="text")
    private String base64picture;


    @ManyToMany
    @JoinTable(name = "comics_characters",
        joinColumns = @JoinColumn(name = "characters_id"),
        inverseJoinColumns = @JoinColumn(name = "comics_id"))
    private Set<Comics> comics = new HashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getDescription() {
        return description;
    }

    public void setDescription(@NonNull String description) {
        this.description = description;
    }

    @NonNull
    public String getBase64picture() {
        return base64picture;
    }

    public void setBase64picture(@NonNull String base64picture) {
        this.base64picture = base64picture;
    }

    public Set<Comics> getComics() {
        return comics;
    }

    public void setComics(Set<Comics> comics) {
        this.comics = comics;
    }

    public void addComics(Comics comics) {
        this.comics.add(comics);
    }
}
