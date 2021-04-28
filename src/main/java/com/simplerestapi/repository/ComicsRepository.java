package com.simplerestapi.repository;


import com.simplerestapi.entity.Comics;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ComicsRepository extends JpaRepository<Comics,Integer> {

    @Query("select c from Comics c where  c.title like %?1% and c.description like %?2%")
    Page<Comics> getComics(String title, String description, PageRequest pageRequest);
}
