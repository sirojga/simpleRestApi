package com.simplerestapi.service;

import com.simplerestapi.dto.CharactersDto;
import com.simplerestapi.dto.ComicsDto;
import com.simplerestapi.entity.Characters;
import com.simplerestapi.entity.Comics;
import com.simplerestapi.exceptions.NotFoundException;
import com.simplerestapi.repository.CharactersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CharactersService {

    @Autowired
    private CharactersRepository repository;

    public Characters saveCharacter(Characters character ){
        return repository.save(character);
    }



public List<CharactersDto> getCharacters( Optional<String> name,
                                          Optional<String> description,
                                          Optional<String> sortBy,
                                          Optional<Integer> page,
                                          Optional<Integer> size){
    return repository.getCharacters(
            name.orElse("_"),
            description.orElse("_"),
            PageRequest.of(page.orElse(0),size.orElse(5),Sort.Direction.ASC, sortBy.orElse("id"))).
            stream().
            map(character -> characterToDto(character)).
            collect(Collectors.toList());

}

    public Characters findById(Integer id) {
        Characters character= new Characters();
        try {
            character = repository.findById(id).get();
        }
        catch (Exception e){
            throw new NotFoundException("There is no character with id="+ id) ;
        }
        return character;
    }

    public Characters findByIdWithFilter( Integer id,
                                           Optional<String> name,
                                           Optional<String> description,
                                           Optional<String> sortBy,
                                           Optional<Integer> page,
                                           Optional<Integer> size) {
        Characters character= new Characters();
        try {
            PageRequest pageRequest = PageRequest.of(page.orElse(0),size.orElse(5),Sort.Direction.ASC, sortBy.orElse("id"));
            character = repository.getCharacters(name.orElse("_"),description.orElse("_"),pageRequest).stream().
                    filter(characters -> characters.getId().equals(id)).
                    collect(Collectors.toList()).get(0);
        }
        catch (Exception e){
            throw new NotFoundException("There is no any characters with id="+ id) ;
        }
        return character;
    }


    public void setCharacterPicture(MultipartFile file, Integer id) throws IOException {
        Characters character = findById(id);
        character.setBase64picture(Base64.getEncoder().encodeToString(file.getBytes()));

        repository.save(character);

    }

    public String getCharacterPicture( Integer id) { return findById(id).getBase64picture(); }

        public CharactersDto characterToDto(Characters character){
        CharactersDto charactersDto = new CharactersDto();
        charactersDto.setId(character.getId());
        charactersDto.setName(character.getName());
        charactersDto.setDescription(character.getDescription());
        charactersDto.setComicsDto(character.getComics().stream().
                map(comics -> comicsToDto(comics)).
                collect(Collectors.toSet()));
        return charactersDto;
    }

    private ComicsDto comicsToDto(Comics comics){
        ComicsDto comicsDto = new ComicsDto();

        comicsDto.setId(comics.getId());
        comicsDto.setTitle(comics.getTitle());
        comicsDto.setDescription(comics.getDescription());

        return comicsDto;

    }

}
