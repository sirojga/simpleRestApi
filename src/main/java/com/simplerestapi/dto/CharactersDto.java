package com.simplerestapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.HashSet;
import java.util.Set;

@JsonInclude(value = JsonInclude.Include.NON_DEFAULT)
public class CharactersDto {

    private Integer id;

    private String name;

    private String description;

    private String base64picture;

    private Set<ComicsDto> comics = new HashSet<>();

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBase64picture() {
        return base64picture;
    }

    public void setBase64picture(String base64picture) {
        this.base64picture = base64picture;
    }

    public Set<ComicsDto> getComicsDto() {
        return comics;
    }

    public void setComicsDto(Set<ComicsDto> comicsDto) {
        this.comics = comicsDto;
    }

}
