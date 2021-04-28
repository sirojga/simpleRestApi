package com.simplerestapi.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.HashSet;
import java.util.Set;


@JsonInclude(value = JsonInclude.Include.NON_DEFAULT)
public class ComicsDto {

    private int id;

    private String title;

    private String description;

    private Set<CharactersDto> characters =new HashSet<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<CharactersDto> getCharactersDto() {
        return characters;
    }

    public void setCharactersDto(Set<CharactersDto> charactersDto) {
        this.characters = charactersDto;
    }
}
