package com.simplerestapi.repository;

import com.simplerestapi.entity.Characters;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;



public interface CharactersRepository extends JpaRepository<Characters,Integer> {

    @Query("select c from Characters c where  c.name like %?1% and c.description like %?2%" )
    Page<Characters> getCharacters( String name, String description, PageRequest pageRequest);


}
