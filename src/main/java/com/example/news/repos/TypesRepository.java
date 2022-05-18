package com.example.news.repos;

import com.example.news.entity.Type;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypesRepository extends CrudRepository<Type, Long> {
    List<Type> findAll();
}
