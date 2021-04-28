package com.simplerestapi.service;

import com.simplerestapi.dto.CharactersDto;
import com.simplerestapi.dto.ComicsDto;
import com.simplerestapi.entity.Characters;
import com.simplerestapi.entity.Comics;
import com.simplerestapi.exceptions.NotFoundException;
import com.simplerestapi.repository.ComicsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ComicsService {

    @Autowired
    private ComicsRepository repository;


    public List<ComicsDto> getComics( Optional<String> name,
                                              Optional<String> description,
                                              Optional<String> sortBy,
                                              Optional<Integer> page,
                                              Optional<Integer> size){
        return repository.getComics(
                name.orElse("_"),
                description.orElse("_"),
                PageRequest.of(page.orElse(0),size.orElse(5), Sort.Direction.ASC, sortBy.orElse("id"))).
                stream().
                map(comics -> comicsToDto(comics)).
                collect(Collectors.toList());

    }

    public Comics saveComics(Comics Comics ){
        return repository.save(Comics);
    }

    public Comics findById(Integer id) {
        Comics comics= new Comics();
        try {
            comics = repository.findById(id).get();
        }
        catch (Exception e){
            throw new NotFoundException("There is no comics with id="+ id) ;
        }
        return comics;
    }

    public ComicsDto comicsToDto(Comics comics){
        ComicsDto comicsDto=new ComicsDto();
        comicsDto.setId(comics.getId());
        comicsDto.setTitle(comics.getTitle());
        comicsDto.setDescription(comics.getDescription());
        comicsDto.setCharactersDto(comics.getCharacters().stream().map(character -> charactersToDto(character)).collect(Collectors.toSet()));
        return comicsDto;
    }

    private CharactersDto charactersToDto(Characters character){
        CharactersDto characterDto = new CharactersDto();
        characterDto.setId(character.getId());
        characterDto.setName(character.getName());
        characterDto.setDescription(character.getDescription());
        return characterDto;
    }
}
