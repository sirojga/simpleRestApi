package com.simplerestapi.controller;

import com.simplerestapi.dto.CharactersDto;
import com.simplerestapi.dto.ComicsDto;
import com.simplerestapi.entity.Characters;
import com.simplerestapi.entity.Comics;
import com.simplerestapi.repository.CharactersRepository;
import com.simplerestapi.repository.ComicsRepository;
import com.simplerestapi.service.CharactersService;
import com.simplerestapi.service.ComicsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@RestController
@RequestMapping("api")
@Validated
public class CharactersController {


    @Autowired
    private CharactersService characterService;

    @Autowired
    private ComicsService comicsService;

    @GetMapping("/characters")
    public List<CharactersDto> getAllCharacters(
            @RequestParam Optional<String> name,
            @RequestParam Optional<String> description,
            @RequestParam Optional<String> sortBy,
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> size){
        return characterService.getCharacters(name,description,sortBy,page,size);
    }

    @GetMapping("/characters/{id}")
    public CharactersDto getCharacterById(@PathVariable Integer id) {
        return characterService.characterToDto(characterService.findById(id));
    }

    @GetMapping("/characters/{id}/comics")
    public Set<ComicsDto> getComicsOfCharacter(@PathVariable Integer id,
                                               @RequestParam Optional<String> title,
                                               @RequestParam Optional<String> description
                                                   ) {
        return characterService.characterToDto(characterService.findById(id)).getComicsDto().stream().
                filter(comicsDto -> comicsDto.getTitle().matches(".*"+ title.orElse("")+ ".*")).
                filter(comicsDto -> comicsDto.getDescription().matches(".*"+ description.orElse("")+ ".*")).
                collect(Collectors.toSet());
    }

    @GetMapping("/characterpicture/{id}")
    public String getCharacterPicture(@PathVariable Integer id){
        return characterService.getCharacterPicture(id);
    }

    @PostMapping("/characters")
    public void addCharacter(@RequestBody Characters character){
        characterService.saveCharacter(character);}

    @PutMapping("/characters/{CharacterId}/comics/{comicsId}")
    public void addComicsToCharacter(
            @PathVariable int CharacterId,
            @PathVariable int comicsId
    ) {
        Characters character = characterService.findById(CharacterId);
        Comics comics = comicsService.findById(comicsId);
        character.addComics(comics);
        characterService.saveCharacter(character);
    }

    @PostMapping("/CharacterPicture")
    public void setCharacterPicture(@RequestParam(name="id") Integer id,
                                      @RequestParam("file") MultipartFile file) throws IOException {
        characterService.setCharacterPicture(file,id);
    }



}
