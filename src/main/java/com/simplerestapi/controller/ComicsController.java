package com.simplerestapi.controller;

import com.simplerestapi.dto.CharactersDto;
import com.simplerestapi.dto.ComicsDto;
import com.simplerestapi.entity.Comics;
import com.simplerestapi.service.CharactersService;
import com.simplerestapi.service.ComicsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api")
public class ComicsController {

    @Autowired
    ComicsService comicsService;

    @GetMapping("/comics")
    public List<ComicsDto> getComics(
            @RequestParam Optional<String> title,
            @RequestParam Optional<String> description,
            @RequestParam Optional<String> sortBy,
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> size){

        return comicsService.getComics(title,description,sortBy,page,size);}


    @GetMapping("/comics/{id}")
    public ComicsDto getComicsById(@PathVariable Integer id){return comicsService.comicsToDto(comicsService.findById(id));}

    @PostMapping("/comics")
    public Comics addComics(@RequestBody Comics comics){ return comicsService.saveComics(comics);}

    @GetMapping("/comics/{id}/characters")
    public Set<CharactersDto> getComicsCharacters(@PathVariable Integer id,
                                                  @RequestParam Optional<String> name,
                                                  @RequestParam Optional<String> description){

        return comicsService.comicsToDto(comicsService.findById(id)).getCharactersDto().stream().
                filter(charactersDto -> charactersDto.getName().matches(".*"+ name.orElse("")+ ".*")).
                filter(charactersDto -> charactersDto.getDescription().matches(".*"+ description.orElse("")+ ".*")).
                collect(Collectors.toSet());}




}
