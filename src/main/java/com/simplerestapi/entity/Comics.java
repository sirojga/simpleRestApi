package com.simplerestapi.entity;

import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
public class Comics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NonNull
    private String title;

    @NonNull
    private String description;


    @ManyToMany(mappedBy = "comics")
    private Set<Characters> characters=new HashSet<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    @NonNull
    public String getDescription() {
        return description;
    }

    public void setDescription(@NonNull String description) {
        this.description = description;
    }

    public Set<Characters> getCharacters() {
        return characters;
    }

    public void setCharacters(Set<Characters> characters) {
        this.characters = characters;
    }

    public void addCharacter(Characters character){
        this.characters.add(character);
    }
}
